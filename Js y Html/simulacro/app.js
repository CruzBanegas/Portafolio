import express from 'express';
import { Op } from 'sequelize';
import sequelize from './db.js';
import StarbucksStore from './models/starbucksStores.js';
import countries from './data/countries.js';

const app = express();
const PORT = 3000;

app.use(express.static('public'));

app.get('/api/locales', async (req, res) => {
    try {
        const { texto = '', hemisferio = 'Todos' } = req.query;

        const condiciones = {};

        // Filtro de texto: busca en nombre o ciudad
        if (texto.trim() !== '') {
            condiciones[Op.or] = [
                { storeName: { [Op.like]: `%${texto}%` } },
                { city: { [Op.like]: `%${texto}%` } },
            ];
        }

        // Filtro de semiemisferio: por latitud y longitud
        if (['NE', 'NO', 'SE', 'SO'].includes(hemisferio)) {
            condiciones.latitude = { [Op.not]: null };
            condiciones.longitude = { [Op.not]: null };

            if (hemisferio.includes('N')) {
                condiciones.latitude[Op.gt] = 0;
            } else {
                condiciones.latitude[Op.lt] = 0;
            }

            if (hemisferio.includes('E')) {
                condiciones.longitude[Op.gt] = 0;
            } else {
                condiciones.longitude[Op.lt] = 0;
            }
        }

        const locales = await StarbucksStore.findAll({
            where: condiciones,
            limit: 15,
        });

        const resultado = locales.map((local) => {
            const lat = local.latitude;
            const lon = local.longitude;

            let semiemisferio = 'Indefinido';
            if (typeof lat === 'number' && typeof lon === 'number') {
                if (lat > 0 && lon > 0) semiemisferio = 'Noreste (NE)';
                else if (lat > 0 && lon < 0) semiemisferio = 'Noroeste (NO)';
                else if (lat < 0 && lon > 0) semiemisferio = 'Sudeste (SE)';
                else if (lat < 0 && lon < 0) semiemisferio = 'Sudoeste (SO)';
            }

            return {
                storeNumber: local.storeNumber,
                storeName: local.storeName,
                streetAddress: local.streetAddress,
                city: local.city,
                country: countries[local.country] ?? local.country,
                semiemisferio,
            };
        });

        res.json(resultado);
    } catch (error) {
        console.error('Error al consultar locales con filtros:', error);
        res.status(500).json({ error: 'Error interno del servidor' });
    }
});

(async function start() {
    // Validar conexión a la base de datos.
    await sequelize.authenticate();

    // Iniciar el servidor
    app.listen(PORT, () => {
        console.log(`Servidor iniciado y escuchando en http://localhost:${PORT}`);
    });
})();

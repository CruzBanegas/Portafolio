import pickle
import funciones as fun


class Ticket:
    def __init__(self, cod, pat, tipo, pago, cabina, distancia, pais):
        self.cod = cod
        self.pat = pat
        self.tipo = tipo
        self.pago = pago
        self.cabina = cabina
        self.dist = distancia
        self.paispat = pais

    def __str__(self):
        return "| Codigo: " + f"{self.cod:^10}" + \
            "| Patente: " + f"{self.pat:^10}" + \
            "| Tipo: " + f"{self.tipo:^15}" + \
            "| Pago: " + f"{self.pago:^11}" + \
            "| Cabina: " + f"{self.cabina:^11}" + \
            "| Distancia: " + f"{self.dist:^6}km" + \
            "| Pais: " + f"{self.paispat:^10}" + " |"


def generador_archivo(B2):
    print('Cargando los datos del archivo', 'peajes-tp4.csv', 'a', B2)
    m = open('peajes-tp4.csv', 'rt')
    t = open(B2, 'wb')
    f_linea = True
    seg_linea = True
    for linea in m:
        if f_linea:
            f_linea = False
            continue
        elif seg_linea:
            seg_linea = False
            continue

        linea = linea.strip()
        if linea == "": 
            continue

        datos = linea.split(',')
        if len(datos) < 6: 
            continue

        cod = datos[0]
        pat = datos[1]
        tipo = datos[2]
        pago = datos[3]
        cabina = datos[4]
        dist = datos[5]
        paispat = fun.patente_pais(pat)
        ppp = Ticket(int(cod), pat, int(tipo), int(pago), int(cabina), int(dist), paispat)
        pickle.dump(ppp, t)
    m.close()
    t.close()
    print("*" * 100)
    print('Los archivos se han cargado correctamente...')
    print("*" * 100)



def carga_datos_manual(B2):
    m = open(B2, 'a+b')
    print()
    n = int(input('Cantidad de tickets que desea registrar: '))
    for i in range(n):
        print('\nTicket', i, ':')
        print('Ingrese el codigo del ticket: ')
        cod = fun.validacion()
        pat = input('Ingrese la patente: ')
        print('Ingrese el tipo de vehículo: ')
        tipo = fun.validar_rango(0, 2)
        print('Ingrese la forma de pago:')
        pago = fun.validar_rango(1, 2)
        print('Ingrese la cabina: ')
        cabina = fun.validar_rango(0, 4)
        print('Ingrese la distancia: ')
        dist = fun.validar_dist(0)
        paispat = fun.patente_pais(pat)
        t = Ticket(int(cod), pat, tipo, pago, cabina, dist, paispat)
        pickle.dump(t, m)
        m.flush()
        print('Se ha completado la carga manua correctamente!')
    m.close()
    print()


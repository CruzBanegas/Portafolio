class Ticket:
    def __init__(self, codigo, patente, tipo_vehiculo, f_pago, pais_cabina, km):
        self.codigo = codigo
        self.patente = patente
        self.tipo_vehiculo = tipo_vehiculo
        self.f_pago = f_pago
        self.pais_cabina = pais_cabina
        self.km = km


    def __str__(self):
        res = "codigo: " + str(self.codigo) + " | patente: " + str(self.patente) \
            + " | tipo de vehiculo: " + str(self.tipo_vehiculo) + " | forma de pago: " \
            + str(self.f_pago) + " | pais de cabina: " + str(self.pais_cabina) \
            + " | kilometros desde la cabina anterior: " + str(self.km)
        return res






#TRABAJO PRACTICO NUMERO 1
#CABINA DE PEAJE DE LA FRONTERA


print("*" * 75)
print("SISTEMA DE CONTROL DE PEAJES")
print(("*" * 75)+"\n")
patente = input("Ingrese la patente del vehiculo: ")
print("Ingrese el número correspondiente al tipo de vehiculo:"+"\n""[0]=Motocicleta"+"\n""[1]=Automovil"+"\n""[2]=Camión")
tipo = int(input("Tipo de vehiculo: "))
print("Ingrese el número correspondiente a la forma de pago:"+"\n""[1]=Manual"+"\n""[2]=Telepeaje")
pago = int(input("Forma de pago: "))
print("Ingrese el número correspondiente al pais atravesado"+"\n""[0]=Argentina"+"\n""[1]=Bolivia"+"\n""[2]=Brasil"+"\n""[3]=Paraguay"+"\n""[4]=Uruguay")
pais = int(input("Pais atravesado: "))
distancia = float(input("Ingrese la distancia recorrida en Kilometros desde la ultima cabina: "))
pais_patente = ""
descuento = 1.0
promedio_km = 0
#proceso
#VALIDACION PATENTE
if (patente >= "A" and patente <= "Z") or (patente >= "0" and patente <= "9") :
    if (patente[0] >= "A" and patente[0] <= "Z") and  len(patente) == 7 :
        if (patente[0:1] >= "A" and patente[0:1] <= "Z") and (patente[2:4] >= "0" and patente[2:4] <= "9") and (patente[5:6] >= "A" and patente[5:6] <= "Z"):
            pais_patente = "al pais ARGENTINA"
        elif (patente[0:2] >= "A" and patente[0:2] <= "Z") and (patente[3] >= "0" and patente[3] <= "9") and (patente[4] >= "A" and patente[4] <= "Z") and (patente[5:6] >= "0" and patente[5:6] <= "9"):

            pais_patente = "al pais BRASIL"
        elif (patente[0:1] >= "A" and patente[0:1] <= "Z") and (patente[2:6] >= "0" and patente[2:6] <= "9") :
            pais_patente = "al pais BOLIVIA"
        elif (patente[0:3] >= "A" and patente[0:3] <= "Z") and (patente[4:6] >= "0" and patente[4:6] <= "9"):
            pais_patente = "al pais PARAGUAY"
        elif (patente[0:2] >= "A" and patente[0:2] <= "Z") and (patente[3:6] >= "0" and patente[3:6] <= "9"):
            pais_patente = "al pais URUGUAY"
        else:
            pais_patente = "a otro pais fuera del Mercosur"
    else:
        pais_patente = "a otro pais fuera del Mercosur"
else:
    pais_patente = "a otro pais fuera del Mercosur"
#TIPO DE PAGO
if tipo == 0:
    tipo_vehiculo = "MOTOCICLETA"
elif tipo == 1:
    tipo_vehiculo = "AUTOMOVIL"
elif tipo == 2:
    tipo_vehiculo = "CAMION"
else:
    tipo_vehiculo = "Valor no reconocido"
#FORMA DE PAGO
if pago == 1:
    pago_forma = "MANUAL"
elif pago == 2:
    pago_forma = "TELEPEAJE"
else:
    pago_forma = "Valor no reconocido"

#Pais atravesado
if pais == 0:
    pais_atravesado = "Argentina"
elif pais == 1:
    pais_atravesado = "Bolivia"
elif pais == 2:
    pais_atravesado = "Brasil"
elif pais == 3:
    pais_atravesado = "Paraguay"
elif pais == 4:
    pais_atravesado = "Uruguay"
else:
    pais_atravesado = "Valor no reconocido"
#Distancia
#CAlCULO IMPORTE
if  pais == 1:
    importe = 200
    #Bolivia
elif pais == 2:
    importe = 400
    #Brasil
else:
    importe = 300
    #cualquier otro pais

#Calcular descuento segun vehiculo
if tipo == 0:
    descuento -= 0.50
    info_desc = "El descuento por el tipo de vehiculo MOTOCICLETA es del 50%"
    #Descuento para motos
elif tipo == 2:
    descuento += 0.60
    info_desc = "El recargo por el tipo de vehiculo CAMION es del 60%"
    #Recargo para camiones
elif tipo == 1:
    info_desc = "No se aplica descuento por tipo de vehiculo AUTOMOVIL"
else:
    info_desc= "No fue registrado un valor reconocido al tipo de vehiculo para aplicar el descuento"
#Calculo del importe
importe_tot = importe * descuento




#TICKET
print("\n"+"*" * 75)
print(("\t"*7)+("EMISIÓN DE TICKET"))
print(("*" * 75)+"\n")


print("La patente del vehiculo pertence",pais_patente)
print("El importe basico al pais atravesado",pais_atravesado,"es de $",importe)
print(info_desc)
# Descuento segun forma de pago
if pago == 2:
    importe_tot *= 0.9
    print("Se aplica un 10% de descuento al importe por forma de pago TELEPEAJE")
    # Descuento por pago de telepeaje
print("El importorte total a pagar es de $",importe_tot)
# Calculo Promedio precio x distancia recorrida
if distancia > 0 :
    promedio_km = importe_tot / distancia
    print("El promedio del valor pagado por los Km recorridos entre cabinas es de $",round(promedio_km, 2))
else:
    print("La cabina actual es la primera que el vehiculo atraviesa, no se aplica el calculo de promedio pagado por km")
# incluir libretas
import ticket
from ticket import *

# definicion de funciones
def mostrar_menu():
    print("\n" + "=" * 70)
    print("{:^70}".format("SISTEMA DE GESTIÓN DE PEAJES - MENÚ DE OPCIONES"))
    print("=" * 70)
    print(" 1. Cargar datos desde archivo (peajes-tp3.txt)")
    print(" 2. Cargar un nuevo ticket manualmente por teclado")
    print(" 3. Mostrar registros ordenados por código de ticket (Ascendente)")
    print(" 4. Buscar ticket por patente y país de cabina")
    print(" 5. Cambiar forma de pago de un ticket (por código)")
    print(" 6. Contar vehículos por país de origen (según patente)")
    print(" 7. Calcular importe acumulado por tipo de vehículo")
    print(" 8. Identificar tipo de vehículo con mayor monto acumulado")
    print(" 9. Calcular distancia promedio y vehículos que la superan")
    print(" 0. Salir")
    print("-" * 70)

def pais_origen(vec):
    paises = ('Argentina', 'Bolivia', 'Brasil', 'Paraguay', 'Uruguay', 'Chile', 'Otro')
    for i, nombre_p in enumerate(paises):
        cont = pp([vec[i]])
        if cont[i] != 0:
            return nombre_p
    return None

def texto_desde_archivo(p1, vec):        # PUNTO 1
    with open(p1, encoding='utf-8') as archivo:
        bel = archivo.readlines()
    l1 = False
    for linea in bel:
        if l1 is False:
            l1 = True
        else:
            codigo = linea[0:10].strip()
            patente = linea[10:17].strip()
            t_vehiculo = linea[17].strip()
            f_pago = linea[18].strip()
            pais_cabina = linea[19].strip()
            km = linea[20:23].strip()
            vec.append(ticket.Ticket(codigo, patente, t_vehiculo, f_pago, pais_cabina, km))

    print('-' * 70)
    print("Se han cargado correctamente los datos")
    print("-" * 70)

def cargar_ticket_desde_teclado():          #PUNTO 2
    codigo = input("ingrese el codigo de indentificacion (solo digitos): ")
    while not codigo.isdigit():
        print("Error, ingrese solamente digitos")
        codigo = input("ingrese el codigo de indentificacion (solo digitos): ")
    while len(codigo) != 10:
        print("Error, ingrese 10 (diez) digitos")
        codigo = input("ingrese el codigo de indentificacion (solo digitos): ")
    patente = input("ingrese la patente (exactamente 7 caracteres): ")
    while len(patente) != 7:
        print("Error, ingrese 7 caracteres")
        patente = input("ingrese la patente (exactamente 7 caracteres): ")
    tipo_vehiculo = input("ingrese el tipo de vehiculo (0: Motocicleta, 1: Automovil, 2: Camion): ")
    while tipo_vehiculo not in ["0", "1", "2"]:
        print("Error, ingrese el nuevo de nuevo")
        tipo_vehiculo = input("ingrese el tipo de vehiculo (0: Motocicleta, 1: Automovil, 2: Camion): ")
    f_pago = input("ingrese el metodo de pago (1: Manual, 2:Telepeaje): ")
    while f_pago not in ["1", "2"]:
        print("Error, ingrese el metodo de pago nuevamente")
        f_pago = input("ingrese el metodo de pago (1: Manual, 2:Telepeaje): ")
    pais_cabina = input("ingrese el pais de la cabina (0: Argentina, 1: Bolivia, 2: Brasil, 3: Paraguay, 4: Uruguay): ")
    while pais_cabina not in ["0", "1", "2", "3", "4"]:
        print("Error, ingrese el pais de la cabina nuevamente")
        pais_cabina = input("ingrese el pais de la cabina (0: Argentina, 1: Bolivia, 2: Brasil, 3: Paraguay, 4: Uruguay): ")
    km = input("ingrese los kilometros realizados desde la anterior cabina (ingrese 0 si es la primera cabina): ")
    while not km.isdigit():
        print("Error, escriba solo digitos")
        km = input("ingrese los kilometros realizados desde la anterior cabina (ingrese 0 si es la primera cabina): ")
    ticket = Ticket(codigo, patente, tipo_vehiculo, f_pago, pais_cabina, km)
    return ticket

def ordenamiento(vec):      #PUNTO 3
    n = len(vec)
    for i in range(n - 1):
        for j in range(i + 1, n):
            if vec[i].codigo > vec[j].codigo:
                vec[i], vec[j] = vec[j], vec[i]

def muestra_de_ordenamiento(vec):       #PUNTO 3
    print("REGISTROS: ")

    if len(vec) >= 2:
        ordenamiento(vec)
    for i in vec:
        print(
            'Código:', i.codigo,
            ' |Patente:', i.patente,
            ' |Tipo de Vehículo:', i.tipo_vehiculo,
            ' |Pago:', i.f_pago,
            ' |Cabina:', i.pais_cabina,
            ' |Distancia:', i.km,
            '\n'
        )

def ejercicio3(vec):        #PUNTO 3
    muestra_de_ordenamiento(vec)

def buscar_patente(vec, p, x):    #PUNTO 4
    for i in vec:
        if i.patente == p and int(i.pais_cabina) == x:
            return i
    return None

def obtener_pais():             #PUNTO 4
    while True:
        x = input('País de la cabina(0: Argentina | 1: Bolivia | 2: Brasil | 3: Paraguay | 4: Uruguay): ')
        if x.isdigit():
            x = int(x)
            if 0 <= x <= 4:
                return x
        print('Error,seleccione un número entre 0 y 4.')

def ejercicio4(vec):        #PUNTO 4
    if not vec:
        print("-" * 70)
        print('No existen registros previos...')
        print("-" * 70)
        return
    print("-" * 70)
    p = input('Ingrese la patente que desea encotar: ')
    print("-" * 70)
    x = obtener_pais()
    pe1 = buscar_patente(vec, p, x)
    if pe1:
        print("-" * 70)
        print('Se encontró la patente!! ')
        print("-" * 70)
        print('DATOS: ', pe1)
        print("-" * 70)
    else:
        print("-" * 70)
        print('Patente no encontrada')
        print("-" * 70)

def buscar_ticket(vec, c):
    for i in vec:
        if int(i.codigo) == c:
            return i
    return None

def val_ticket(mensaje='Código del ticket: '):
    while True:
        n = input(mensaje)
        if str(n).isdigit():
            return int(n)
        print('Error, ingrese un código válido.')

def ejercicio5(vec):
    if not vec:
        print('No hay registros guardados.')
        return

    codigo_a_buscar = val_ticket(mensaje='Código a buscar: ')
    ticket_encontrado = buscar_ticket(vec, codigo_a_buscar)

    if ticket_encontrado is not None:
        ticket_encontrado.f_pago = 2 if int(ticket_encontrado.f_pago) == 1 else 1
        print('Código encontrado - Cambios realizados')
        print('Registro actualizado: ', ticket_encontrado)
        return

    print('\n- Código no encontrado...\n')



def is_tipo_1(patente):     #Punto 6
    return patente[0].isalpha() and patente[2:4].isdigit() and patente[5].isalpha()

def is_tipo_2(patente):
    return patente[0] == ' ' and patente[1:4].isalpha() and patente[5].isdigit()

def is_tipo_3(patente):
    return patente[0:2].isalpha() and patente[3].isdigit() and patente[4].isalpha() and patente[5:6].isdigit()

def is_tipo_4(patente):
    return patente[0:1].isalpha() and patente[2:7].isdigit()

def is_tipo_5(patente):
    return patente[0:2].isalpha() and patente[3:6].isdigit()

def is_tipo_6(patente):
    return patente[0:3].isalpha() and patente[4:6].isdigit()

def pp(vec):
    cont = [0] * 7
    for i in vec:
        if is_tipo_1(i.patente):
            cont[0] += 1
        elif is_tipo_2(i.patente):
            cont[5] += 1
        elif is_tipo_3(i.patente):
            cont[2] += 1
        elif is_tipo_4(i.patente):
            cont[1] += 1
        elif is_tipo_5(i.patente):
            cont[4] += 1
        elif is_tipo_6(i.patente):
            cont[3] += 1
        else:
            cont[6] += 1
    return cont


def ejercico6(vec):
    if not vec:
        print('No hay registros guardados.')
        return
    p_a_i_s = pp(vec)
    paises = ['Argentina', 'Bolivia', 'Brasil', 'Paraguay', 'Uruguay', 'Chile', 'Otro país']
    print('Cantidad de vehículos de cada país que pasaron por las cabinas:')
    for i in range(len(paises)):
        print(f'Vehículos de {paises[i]}: {p_a_i_s[i]}')



def calcular_base(pais_cabina):
    if pais_cabina == 2:
        return 400
    elif pais_cabina == 1:
        return 200
    else:
        return 300

def calcular_descuento(tipo_vehiculo, base):
    if tipo_vehiculo == 0:
        return base * 0.5
    elif tipo_vehiculo == 1:
        return base
    else:
        return base * 1.6

def calcular_importe_final(vec):
    base = calcular_base(int(vec.pais_cabina))
    descuento = calcular_descuento(int(vec.tipo_vehiculo), base)
    importe_final = descuento if int(vec.f_pago) == 1 else descuento - (descuento * 0.1)
    return importe_final

def ejercicio7(v):
    if not v:
        print('No hay registros guardados.')
        return [0, 0, 0]
    acum = [0, 0, 0]

    for i in v:
        tipo_vehiculo = int(i.tipo_vehiculo)
        acum[tipo_vehiculo] += calcular_importe_final(i)

    print('\nImporte acumulado por tipo de vehículo: ')
    tipos_vehiculos = ['Motocicletas', 'Automóviles', 'Camiones']

    for i, tipo in enumerate(tipos_vehiculos):
        print('\n- {}: ${}'.format(tipo, acum[i]), end=' ')

    print('\n')
    return acum

def porcentaje(acumulador, contador):
    if contador != 0:
        return round((acumulador * 100) / contador, 2)
    return None

def ejercicio8(vec):
    if not vec:
        print('No hay registros guardados.')
        return
    acumulador = ejercicio7(vec)
    acumulador2 = sum(acumulador)

    tipo_vehiculo = ["Motocicletas", "Automóviles", "Camiones"]
    mayor_index = acumulador.index(max(acumulador))

    print(f'Tipo de vehículo con mayor monto acumulado: {tipo_vehiculo[mayor_index]} - monto: ${max(acumulador)}')

    porc = porcentaje(max(acumulador), acumulador2)
    print("-" * 70)
    print(f'- Representa un {porc:.2f}% del total.\n')
    print("-" * 70)

def promedio(acumulador, contador):
    promedio = 0
    if contador != 0:
        promedio = acumulador / contador
    return promedio

def ejercicio9(vec):
    if not vec:
        print('No hay registros guardados.')
        return
    distancias = [int(vehiculo.km) for vehiculo in vec]
    prom_distancia = sum(distancias) / len(distancias)
    vehiculos_mayor_promedio = sum(1 for distancia in distancias if distancia > prom_distancia)
    print("-" * 70)
    print('\nEl promedio de distancia recorrida fue: ', prom_distancia, 'km')
    print("-" * 70)
    print('El numero de vehiculos que superaron la distancia promedio fue: ', vehiculos_mayor_promedio, '\n')
    print("-" * 70)


#funcion principal
def principal():
    # menu de opciones
    vec = []
    opcion = None
    o1 = True
    while opcion != 0:
        mostrar_menu()
        op = input("Ingrese una opcion: ")
        if not op.isdigit():
            print("Error, vuelva a elegir una opcion: ")
            continue
        opcion = int(op)
        #evaluar opciones del usuario
        if opcion == 1:

            if o1:
                texto_desde_archivo('peajes-tp3.txt', vec)
                o1 = False
            else:
                print('Ya hay registros guardados')
                conf = input('¿Quiere remplazar los registros anteriores por el actual? (A(aceptar), R(rechazar)): ')
                if conf.lower() == "a":
                    vec = []
                    texto_desde_archivo('peajes-tp3.txt', vec)

                if conf.lower() not in ["a", "r"]:
                    print("Debe seleccionar una de las dos opciones")
                    conf = input("seleccione A(aceptar), R(rechazar): ")
                    if conf.lower() == "a":
                        vec = []
                        texto_desde_archivo('peajes-tp3.txt', vec)
                o1 = False

        elif opcion == 2:
            nuevo_ticket = cargar_ticket_desde_teclado()
            vec.append(nuevo_ticket)
            print("Ticket cargado correctamente.")
        elif opcion == 3:
            ejercicio3(vec)
        elif opcion == 4:
            ejercicio4(vec)
        elif opcion == 5:
            ejercicio5(vec)
        elif opcion == 6:
            ejercico6(vec)
        elif opcion == 7:
            ejercicio7(vec)
        elif opcion == 8:
            ejercicio8(vec)
        elif opcion == 9:
            ejercicio9(vec)
        elif opcion == 0:
            print("----------------- Muchas gracias, fin del programa -----------------")
        else:
            print("Error, vuelva a elegir una opcion: ")


if __name__ == '__main__':
    principal()
import pickle
import os.path

def validacion():
    n = ''
    while not n.isdigit():
        n = input('Ingrese un número: ')
        if not n.isdigit():
            print('\tERROR debe ingresar un número')
    return n


def validar_rango(limite_inferior, limite_superior):
    n = limite_inferior - 1
    while n < limite_inferior or n > limite_superior:
        n = int(input('Número entre ' + str(limite_inferior) + ' y ' + str(limite_superior) + ' :'))
        if n < limite_inferior or n > limite_superior:
            print('ERROR cargue nuevamente... ')
    return n


def validar_dist(num):
    n = num - 1
    while n < num:
        n = int(input('Valor mayor o igual a ' + str(num) + ': '))
        if n < num:
            print('\tError...cargue de nuevo')
    return n



def validate_opciones(var):
    return var in ("1", "2", "3", "4", "5", "6", "7", "8", "0")


def menu(opciones, titulo="Gestión de Cabinas de Peaje [4.0]"):
    print("=" * 127)
    print("{:^127}".format("--" + titulo + "--"))
    print("=" * 127 + "\n")
    for i in range(len(opciones)):
        print(f"{i + 1:>2} - {opciones[i]}")
    print(f"{0:>2} - Salir")
    opcion = input("Ingrese una de las opciones: ")
    while not validate_opciones(opcion):
        print("Error, ingrese una opcion valida")
        opcion = input("Ingrese una de las opciones: ")
    print()
    return int(opcion)


def patente_pais(pat):
    if len(pat) < 6:
        return 'Otro'
    if str(pat[0]).isalpha() and str(pat[2:4]).isdigit() and str(pat[5]).isalpha():
        pais = 'Argentina'
    elif str(pat[1:4]).isalpha() and str(pat[5]).isdigit():
        pais = 'Chile'
    elif str(pat[0:2]).isalpha() and str(pat[3]).isdigit() and str(pat[4]).isalpha() and str(
            pat[5:6]).isdigit():
        pais = 'Brasil'
    elif str(pat[0]).isalpha() and str(pat[1]).isalpha() and str(pat[2:6]).isdigit():
        pais = 'Bolivia'
    elif str(pat[0:2]).isalpha() and str(pat[3:6]).isdigit():
        pais = 'Uruguay'
    elif str(pat[0:3]).isalpha() and str(pat[4:6]).isdigit():
        pais = 'Paraguay'
    else:
        pais = 'Otro'
    return pais

def mostrar_datos_arcbin(B):
    if not os.path.exists(B):
        print("*" * 100)
        print('El archivo', B, 'no existe')
        print("*" * 100)
        return
    pl = os.path.getsize(B)
    m = open(B, 'rb')
    print('Archivo binario', B, ':')
    while m.tell() < pl:
        ticket = pickle.load(m)
        print(ticket)
    m.close()
    print()

def search(B2, p):
    manager_bin = open(B2, "rb")
    t = os.path.getsize(B2)
    cont = 0
    while manager_bin.tell() < t:
        ticket = pickle.load(manager_bin)
        if ticket.pat == p:
            cont += 1
            print(ticket, end="\n")

    manager_bin.close()
    return cont


def search2(c, B2):
    m = open(B2, 'rb')
    t = os.path.getsize(B2)
    while m.tell() < t:
        ticket = pickle.load(m)
        if ticket.cod == c:
            print('Se encontro el codigo!!!')
            print(ticket)
            break
    else:
        print("*" * 100)
        print("No se ha encontrado el codigo...")
        print("*" * 100)

def conteo(B2):
    if not os.path.exists(B2):
        print("*" * 100)
        print("El archivo", B2, 'no se encuentra generado')
        print("*" * 100)
        return
    print()
    cf, cc = 3, 5
    cant = [cc * [0] for f in range(cf)]
    m = open(B2, 'rb')
    ppp = os.path.getsize(B2)

    while m.tell() < ppp:
        ticket = pickle.load(m)
        f = int(ticket.tipo)
        c = int(ticket.cabina)
        cant[f][c] += 1
    return cant

def punto6(B2):
    if not os.path.exists(B2):
        print("*" * 100)
        print("El archivo", B2, 'no se encuentra generado')
        print("*" * 100)
        return
    cf, cc = 3, 5
    cant = conteo(B2)
    print('Cantidad de vehículos por tipo y por cabina')
    for f in range(cf):
        for c in range(cc):
            if cant[f][c] != 0:
                print('Tipo de vehículo:', f, 'Cabina:', c, '- Cantidad:', cant[f][c])
    return cant


def filas(B2):
    cont = conteo(B2)
    m, n = len(cont), len(cont[0])
    print("*"*100)
    print('Cantidad de vehículos por cada tipo: ')
    for f in range(m):
        ac = 0
        for c in range(n):
            ac += cont[f][c]
        print('Tipo', f, '\t- Cantidad total de vehículos:', ac)


def columnas(B2):
    cont = conteo(B2)
    m, n = len(cont), len(cont[0])
    print()
    print('\nCantidad de vehículos por cabina: ')
    for c in range(n):
        ht = 0
        for f in range(m):
            ht += cont[f][c]
        print('Cabina', c, '- Cantidad total de vehículos:', ht)


def promedio(acum, cont):
    if cont != 0:
        return round(acum / cont, 2)
    else:
        return None


def distancia_promedio(B2):
    m = open(B2, 'rb')
    ppp = os.path.getsize(B2)
    acum = 0
    contador = 0
    while m.tell() < ppp:
        ticket = pickle.load(m)
        acum += int(ticket.dist)
        contador += 1
    prom = promedio(acum, contador)
    return prom


def arreglo(B2):
    m = open(B2, 'rb')
    ppp = os.path.getsize(B2)
    vec = []
    promedio = distancia_promedio(B2)
    while m.tell() < ppp:
        ticket = pickle.load(m)
        if int(ticket.dist) > promedio:
            vec.append(ticket)
    return vec


def shellsort(vec):
    n = len(vec)
    h = 1
    while h <= n // 9:
        h = 3 * h + 1
    while h > 0:
        for j in range(h, n):
            y = vec[j]  
            k = j - h
            while k >= 0 and y.dist < vec[k].dist: # Comparamos usando .dist
                vec[k + h] = vec[k]  # Movemos el objeto completo
                k -= h
            vec[k + h] = y  # Insertamos el objeto completo
        h //= 3

def ds(v):
    for i in v:
        print('\nCódigo: ' + str(i.cod) + ' - Patente: ' + str(i.pat) + ' - Tipo de vehículo: ' + str(i.tipo)
              + ' - Forma de pago: ' + str(i.pago) + ' - Cabina: ' + str(i.cabina) +
              ' - Distancia: ' + str(i.dist) + ' - Pais al que pertenece: ' +
              str(i.paispat))


def punto8(B2):
    if not os.path.exists(B2):
        print("*" * 100)
        print("El archivo", B2, 'no se encuentra generado')
        print("*" * 100)
        return
    prom = distancia_promedio(B2)
    print('La distancia promedio de todos los vehiculos es:', prom)
    print('\tLa cantidad de vehiculos que superaron la distancia promedio (' + str(prom) + ')')
    v = arreglo(B2)
    shellsort(v)
    x = input(" Presione A para ver aquellos vehiculos cuya distancia sea mayora a la del promedio")
    if x in "aA":
        ds(v)



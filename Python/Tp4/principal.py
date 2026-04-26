import os.path
import funciones as fun
import clase



def inicio():

    B2 ="tp4-peajes"

    opcion = None
    opciones = ("Crear el archivo binario.",
                "Cargar por teclado los detos de un ticket.",
                "Mostrar todos los datos de todos los registros del archivo binario.",
                "Mostrar todos los registros del archivo binario cuya patente sea igual a p.",
                "Buscar si existe en el archivo binario un registro cuyo cod de ticket sea igual a c.",
                "Determinar y mostrar la cantidad de vehiculos de cada combinacion posible.",
                "Mostrar los datos de la matriz anterior.",
                "Calcular y mostrar la distancia promedio.")

    while opcion != 0:
        opcion = fun.menu(opciones)

        if opcion == 1:
            clase.generador_archivo(B2)

        elif opcion == 2:
            clase.carga_datos_manual(B2)

        elif opcion == 3:
            fun.mostrar_datos_arcbin(B2)
        elif opcion == 4:
            if not os.path.exists(B2):
                print("*" * 100)
                print("El archivo", B2, 'no se encuentra generado')
                print("*" * 100)
            else:
                p = input("Ingrese la patente a comparar: ")
                patt = fun.search(B2, p)
                if patt == 0:
                    print("No existe la patente")
                else:
                    print("Se encontaron: ", patt, "patentes")

        elif opcion == 5:
            if not os.path.exists(B2):
                print("*" * 100)
                print("El archivo", B2, 'no se encuentra generado')
                print("*" * 100)
                return
            else:
                c = int(input('\nIngrese el código a buscar: '))
                fun.search2(c, B2)
        elif opcion == 6:
            fun.punto6(B2)

        elif opcion == 7:
            if not os.path.exists(B2):
                print("*" * 100)
                print("El archivo", B2, 'no se encuentra generado')
                print("*" * 100)
                return
            else:
                fun.filas(B2)
                fun.columnas(B2)

        elif opcion == 8:
            fun.punto8(B2)

if __name__ == '__main__':
    inicio()

import sqlite3

# Conectar a la base de datos (se crea automáticamente si no existe)
conn = sqlite3.connect('usuarios.db')

cur = conn.cursor()

cur.execute('''
    CREATE TABLE IF NOT EXISTS usuarios (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        nombre TEXT NOT NULL,
        apellido1 TEXT NOT NULL,
        apellido2 TEXT NOT NULL,
        dni TEXT NOT NULL UNIQUE,
        usuario TEXT NOT NULL UNIQUE,
        contraseña TEXT NOT NULL,
        admin BOOLEAN NOT NULL
    )
''')

conn.commit()
conn.close()

print("Base de datos y tabla 'usuarios' creadas exitosamente.")

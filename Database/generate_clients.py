import psycopg2
from faker import Faker
import random

# Database connection details
DB_HOST = 'localhost'
DB_NAME = 'transaction_db'
DB_USER = 'postgres'
DB_PASSWORD = 'hhaddoucOP'

fake = Faker()

# Connect to the PostgreSQL database


def connect_to_db():
    try:
        conn = psycopg2.connect(
            host=DB_HOST,
            database=DB_NAME,
            user=DB_USER,
            password=DB_PASSWORD
        )
        return conn
    except Exception as e:
        print(f"Error connecting to database: {e}")
        return None

# Function to generate and insert clients into the database


def generate_and_insert_clients(cursor, num_clients=1000):
    clients = []
    for _ in range(num_clients):
        name = fake.name()
        email = fake.email()
        clients.append((name, email))

    try:
        # Bulk insert using executemany
        cursor.executemany(
            "INSERT INTO clients (name, email) VALUES (%s, %s)",
            clients
        )
        print(f"Successfully inserted {num_clients} clients.")
    except Exception as e:
        print(f"Error inserting clients: {e}")

# Main function to execute the script


def main():
    conn = connect_to_db()
    if conn:
        cursor = conn.cursor()
        try:
            # Generate and insert clients into the database
            generate_and_insert_clients(cursor)
            conn.commit()  # Commit the changes to the database
        except Exception as e:
            print(f"Error generating and inserting clients: {e}")
            conn.rollback()
        finally:
            cursor.close()
            conn.close()


if __name__ == "__main__":
    main()


import psycopg2
import random
import faker
from datetime import datetime
import time

DB_HOST = 'localhost'
DB_NAME = 'transaction_db'
DB_USER = 'postgres'
DB_PASSWORD = 'hhaddoucOP'

fake = faker.Faker()


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
        print(f"❌ Error connecting to database: {e}")
        return None


def get_client_ids(cursor):
    # Ensure column name is correct
    cursor.execute("SELECT client_id FROM clients")
    client_ids = [row[0] for row in cursor.fetchall()]
    print(f"✅ Found {len(client_ids)} valid client IDs.")
    return client_ids


def insert_transactions(cursor, conn, client_ids, batch_size, total_rows):
    transactions = []
    start_time = time.time()

    for i in range(total_rows):
        date = fake.date_this_decade()
        montant = round(random.uniform(10.0, 1000.0), 2)
        client_id = random.choice(client_ids)
        transactions.append((date, montant, client_id))

        if len(transactions) >= batch_size:
            try:
                cursor.executemany(
                    "INSERT INTO transactions (date, montant, client_id) VALUES (%s, %s, %s)",
                    transactions
                )
                conn.commit()  # Explicit commit here
                transactions.clear()
                print(f"✅ Inserted {i + 1} rows, Committed at {i + 1}...")
            except Exception as e:
                print(f"❌ Batch insertion error at {i + 1}: {e}")
                conn.rollback()

    if transactions:
        try:
            cursor.executemany(
                "INSERT INTO transactions (date, montant, client_id) VALUES (%s, %s, %s)",
                transactions
            )
            conn.commit()
            print(f"✅ Inserted final batch of {len(transactions)} rows.")
        except Exception as e:
            print(f"❌ Error inserting final batch: {e}")
            conn.rollback()

    print(
        f"✅ Completed {total_rows} insertions in {time.time() - start_time:.2f} seconds.")


def main():
    conn = connect_to_db()
    if conn:
        cursor = conn.cursor()
        try:
            client_ids = get_client_ids(cursor)
            if not client_ids:
                print("❌ No clients found. Insert clients first!")
                return

            insert_transactions(cursor, conn, client_ids,
                                batch_size=10000, total_rows=10000000)
            print("✅ All transactions inserted successfully!")
        except Exception as e:
            print(f"❌ Error inserting transactions: {e}")
            conn.rollback()
        finally:
            cursor.close()
            conn.close()


if __name__ == "__main__":
    main()

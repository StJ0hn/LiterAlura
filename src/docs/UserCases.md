# Use Cases Document - LiterAlura

**Primary Actor:** User (via Command Line Interface - CLI)

## UC01 - Search book by title (Option 1)
* **Objective:** Search for a book using the external Gutendex API and save it to the local database.
* **Main Flow:**
    1. The system displays the main menu and prompts for an option.
    2. The user selects option `1`.
    3. The system prompts the user to enter the title of the book.
    4. The user inputs the title.
    5. The system (Service layer) consumes the external Gutendex API and parses the JSON response.
    6. The system checks if the author of the retrieved book already exists in the local database. If not, it creates a new author record.
    7. The system saves the book and establishes the relationship with the author.
    8. The system prints the details of the saved book (Title, Author, Language, Downloads) to the console.
* **Alternative Flows / Exceptions:**
    * *A1 - Book not found in API:* The system displays "Livro não encontrado na API." (Book not found) and returns to the menu.
    * *A2 - Book already registered:* The database rejects the duplicate entry (due to unique constraints), the system catches the exception, and displays "Atenção: Este livro já foi salvo anteriormente." (Attention: This book was already saved).

## UC02 - List registered books (Option 2)
* **Objective:** Display a list of all books currently saved in the local database.
* **Main Flow:**
    1. The system displays the main menu.
    2. The user selects option `2`.
    3. The system retrieves all book records from the database.
    4. The system iterates through the records and prints the details of each book to the console.
* **Alternative Flows / Exceptions:**
    * *A1 - No books registered:* If the database is empty, the system displays "Nenhum livro registrado ainda." (No books registered yet) and returns to the menu.

## UC03 - List registered authors (Option 3)
* **Objective:** Display a list of all authors currently saved in the local database.
* **Main Flow:**
    1. The system displays the main menu.
    2. The user selects option `3`.
    3. The system retrieves all author records from the database.
    4. The system iterates through the records and prints the details of each author (Name, Birth Year, Death Year) to the console.
* **Alternative Flows / Exceptions:**
    * *A1 - No authors registered:* If the database is empty, the system displays "Nenhum autor registrado ainda." (No authors registered yet) and returns to the menu.

## UC04 - List living authors in a specific year (Option 4)
* **Objective:** Search for and display authors from the local database who were alive during a year specified by the user.
* **Main Flow:**
    1. The system displays the main menu.
    2. The user selects option `4`.
    3. The system prompts the user to enter a specific year.
    4. The user inputs a valid year (integer).
    5. The system queries the database (via custom JPQL) for authors whose birth year is less than or equal to the given year AND whose death year is greater than or equal to the given year.
    6. The system prints the details of the matching authors to the console.
* **Alternative Flows / Exceptions:**
    * *A1 - No living authors found:* If the query returns an empty list, the system displays "Nenhum autor vivo registrado para o ano de [year]" (No living author registered for the year [year]).

## UC05 - List books in a specific language (Option 5)
* **Objective:** Search for and display books from the local database that match a specific language code.
* **Main Flow:**
    1. The system displays the main menu.
    2. The user selects option `5`.
    3. The system prompts the user to enter a language code (e.g., pt, en, es, fr).
    4. The user inputs the desired language code.
    5. The system queries the database for books matching the provided language string.
    6. The system prints the details of the matching books to the console.
* **Alternative Flows / Exceptions:**
    * *A1 - No books found for language:* If the query returns an empty list, the system displays "Nenhum livro encontrado para o idioma: [language]" (No book found for language: [language]).
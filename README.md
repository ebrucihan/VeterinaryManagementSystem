# Veterinary Management System

This project is a comprehensive Veterinary Management System designed to manage doctors, customers, animals, vaccines, appointments, and available dates for a veterinary clinic. The system provides RESTful API endpoints to perform CRUD operations and manage clinic resources effectively.

## UML Diagram

Below is the UML diagram that illustrates the overall structure and relationships within the project:

![Uml-Diagram](https://github.com/user-attachments/assets/e5eb7689-5d7c-441c-804b-c6fcd6bacc39)


## Endpoints

### AnimalController

| Operation              | Endpoint                         | Method | Description                                    |
|------------------------|----------------------------------|--------|------------------------------------------------|
| Save Animal            | `/animals`                       | POST   | Creates a new animal.                          |
| Update Animal          | `/animals`                       | PUT    | Updates details of an existing animal.         |
| Get All Animals        | `/animals`                       | GET    | Retrieves details of all animals.              |
| Get Animal by ID       | `/animals/{id}`                  | GET    | Retrieves details of an animal by ID.          |
| Search Animal by Name  | `/animals/search?name={name}`    | GET    | Searches for animals by name.                  |
| Delete Animal          | `/animals/{id}`                  | DELETE | Deletes an animal by ID.                       |

### CustomerController

| Operation                | Endpoint                           | Method | Description                                    |
|--------------------------|------------------------------------|--------|------------------------------------------------|
| Save Customer            | `/customers`                       | POST   | Creates a new customer.                        |
| Update Customer          | `/customers`                       | PUT    | Updates details of an existing customer.       |
| Get All Customers        | `/customers`                       | GET    | Retrieves details of all customers.            |
| Get Customer by ID       | `/customers/{id}`                  | GET    | Retrieves details of a customer by ID.         |
| Search Customer by Name  | `/customers/search?name={name}`    | GET    | Searches for customers by name.                |
| Get Customer Animals     | `/customers/{id}/animals`          | GET    | Retrieves all animals belonging to a customer. |
| Delete Customer          | `/customers/{id}`                  | DELETE | Deletes a customer by ID.                      |

### VaccineController

| Operation                | Endpoint                           | Method | Description                                    |
|--------------------------|------------------------------------|--------|------------------------------------------------|
| Save Vaccine             | `/vaccines`                        | POST   | Creates a new vaccine.                         |
| Update Vaccine           | `/vaccines`                        | PUT    | Updates details of an existing vaccine.        |
| Get All Vaccines         | `/vaccines`                        | GET    | Retrieves details of all vaccines.             |
| Get Vaccine by ID        | `/vaccines/{id}`                   | GET    | Retrieves details of a vaccine by ID.          |
| Delete Vaccine           | `/vaccines/{id}`                   | DELETE | Deletes a vaccine by ID.                       |

### AnimalVaccineController

| Operation                | Endpoint                           | Method | Description                                    |
|--------------------------|------------------------------------|--------|------------------------------------------------|
| Save AnimalVaccine       | `/animal-vaccines`                 | POST   | Creates a new animal vaccine record.           |
| Update AnimalVaccine     | `/animal-vaccines`                 | PUT    | Updates details of an existing animal vaccine record. |
| Get AnimalVaccine by ID  | `/animal-vaccines/{id}`            | GET    | Retrieves details of an animal vaccine record by ID.   |
| Get Vaccines by Date     | `/animal-vaccines/date?startDate={startDate}&endDate={endDate}` | GET | Retrieves vaccine records within a date range. |

### DoctorController

| Operation                | Endpoint                           | Method | Description                                    |
|--------------------------|------------------------------------|--------|------------------------------------------------|
| Save Doctor              | `/doctors`                         | POST   | Creates a new doctor.                          |
| Update Doctor            | `/doctors`                         | PUT    | Updates details of an existing doctor.         |
| Get All Doctors          | `/doctors`                         | GET    | Retrieves details of all doctors.              |
| Get Doctor by ID         | `/doctors/{id}`                    | GET    | Retrieves details of a doctor by ID.           |
| Delete Doctor            | `/doctors/{id}`                    | DELETE | Deletes a doctor by ID.                        |

### AvailableDateController

| Operation                  | Endpoint                             | Method | Description                                    |
|----------------------------|--------------------------------------|--------|------------------------------------------------|
| Save AvailableDate         | `/available-dates`                   | POST   | Creates a new available date for a doctor.     |
| Update AvailableDate       | `/available-dates`                   | PUT    | Updates details of an existing available date. |
| Get All AvailableDates     | `/available-dates`                   | GET    | Retrieves details of all available dates.      |
| Get AvailableDate by ID    | `/available-dates/{id}`              | GET    | Retrieves details of an available date by ID.  |
| Get Dates by Doctor ID     | `/available-dates/doctor/{doctorId}` | GET    | Retrieves all available dates for a specific doctor. |
| Delete AvailableDate       | `/available-dates/{id}`              | DELETE | Deletes an available date by ID.               |

### AppointmentController

| Operation                  | Endpoint                                 | Method | Description                                    |
|----------------------------|------------------------------------------|--------|------------------------------------------------|
| Save Appointment           | `/appointments`                          | POST   | Creates a new appointment.                     |
| Update Appointment         | `/appointments`                          | PUT    | Updates details of an existing appointment.    |
| Get All Appointments       | `/appointments`                          | GET    | Retrieves details of all appointments.         |
| Get Appointment by ID      | `/appointments/{id}`                     | GET    | Retrieves details of an appointment by ID.     |
| Get Appointments by Doctor | `/appointments/doctor?doctorId={doctorId}&startDateTime={startDateTime}`        | GET    | Retrieves all appointments for a specific doctor starting from the given date and time. |
| Get Appointments by Animal | `/appointments/animal?animalId={animalId}&startDateTime={startDateTime}`        | GET    | Retrieves all appointments for a specific animal starting from the given date and time. |
| Delete Appointment         | `/appointments/{id}`                     | DELETE | Deletes an appointment by ID.                  |

## Technologies Used

- **Java**: The core programming language used for developing the application.
- **Spring Boot**: Framework used for building the backend services.
- **Hibernate**: ORM tool for database operations.
- **PostgreSQL/MySQL**: The relational databases used for data storage.
- **ModelMapper**: Used for object mapping.
- **Jakarta Validation**: For validating API request bodies.

## Installation

1. **Clone the repository**:

   ```bash
   git clone https://github.com/ebrucihan/VeterinaryManagementSystem.git

package ru.xiitori.crudservice.dto.client;

import java.time.LocalDate;
import java.util.Date;

public class ClientInfoDTO {

        private String username;

        private String name;

        private String surname;

        private String lastname;

        private LocalDate dateOfBirth;

        private String email;

        private String phoneNumber;

        public ClientInfoDTO(String username, String name, String surname, String lastname, String email, String phoneNumber) {
            this.username = username;
            this.name = name;
            this.surname = surname;
            this.lastname = lastname;
            this.email = email;
            this.phoneNumber = phoneNumber;
        }

        public ClientInfoDTO() {
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public LocalDate getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
}

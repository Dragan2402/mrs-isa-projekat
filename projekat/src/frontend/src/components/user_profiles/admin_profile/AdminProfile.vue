<template>
  <nav>
    <div class="nav nav-tabs justify-content-center mt-2" id="nav-tab" role="tablist">
      <button class="nav-link active" id="nav-home-tab" data-bs-toggle="tab" data-bs-target="#nav-profile" type="button"
              role="tab" aria-controls="nav-profile" aria-selected="true">Profile
      </button>
      <button class="nav-link" id="nav-profile-tab" data-bs-toggle="tab" data-bs-target="#nav-entities" type="button"
              role="tab" aria-controls="nav-entities" aria-selected="false">Entities
      </button>
      <button class="nav-link" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-reviews" type="button"
              role="tab" aria-controls="nav-reviews" aria-selected="false">Reviews
      </button>
      <button class="nav-link" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-complaints" type="button"
              role="tab" aria-controls="nav-complaints" aria-selected="false">Complaints
      </button>
      <button class="nav-link" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-requests" type="button"
              role="tab" aria-controls="nav-requests" aria-selected="false">Requests
      </button>
      <button class="nav-link" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-reports" type="button"
              role="tab" aria-controls="nav-reports" aria-selected="false">Reports
      </button>
      <button class="nav-link" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-register" type="button"
              role="tab" aria-controls="nav-register" aria-selected="false">Register
      </button>
      <button class="nav-link" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-fee" type="button"
              role="tab" aria-controls="nav-fee" aria-selected="false">Fee
      </button>
      <button class="nav-link" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-reservations"
              type="button" role="tab" aria-controls="nav-reservations" aria-selected="false">Reservations
      </button>
      <button class="nav-link" id="nav-contact-tab" data-bs-toggle="tab" data-bs-target="#nav-loyalty" type="button"
              role="tab" aria-controls="nav-loyalty" aria-selected="false">Loyalty
      </button>
    </div>
  </nav>



  <div class="tab-content" id="nav-tabContent">
    <div class="tab-pane fade show active" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
      <div>
        <div>
          <div>
            <img v-bind:src="'data:image/jpeg;base64,' + profilePicture" alt="Profile picture"
                 style="width: 200px; height: 200px">
          </div>
          <div>
            <div><b>First Name: </b> {{ loggedAdmin.firstName }}</div>
            <div><b>Last Name: </b> {{ loggedAdmin.lastName }}</div>
            <div><b>E-mail: </b> {{ loggedAdmin.email }}</div>
            <div><b>Address: </b> {{ loggedAdmin.address }}</div>
            <div><b>Place: </b> {{ loggedAdmin.city }}</div>
            <div><b>Country: </b> {{ loggedAdmin.country }}</div>
            <div><b>Phone Number: </b> {{ loggedAdmin.phoneNum }}</div>
          </div>
        </div>
        <div>
          <button type="button" class="custom-btn button-primary" data-bs-toggle="modal" data-bs-target="#infoModal">Change Info</button>
        </div>


        <div class="modal fade" id="infoModal" tabindex="-1" aria-hidden="true">
          <div class="modal-dialog modal-lg">
            <div class="modal-content">
              <div class="modal-body">
                <img v-bind:src="'data:image/jpeg;base64,' + profilePicture" alt="Profile picture" style="width: 200px; height: 200px">
                <div>
                  <input type="file" @change="changePicture" accept="image/png, image/jpeg">
                </div>
                <div class="form-floating mb-3">
                  <input v-model="loggedAdmin.firstName" type="text" class="form-control" id="modalFirstName" placeholder="First Name">
                  <label for="modalFirstName">First Name</label>
                </div>
                <div class="form-floating mb-3">
                  <input v-model="loggedAdmin.lastName" type="text" class="form-control" id="modalLastName" placeholder="Last Name">
                  <label for="modalLastName">Last Name</label>
                </div>
                <div class="form-floating mb-3">
                  <input v-model="loggedAdmin.email" type="email" class="form-control" id="modalEmail" placeholder="E-mail">
                  <label for="modalEmail">E-mail</label>
                </div>
                <div class="form-floating mb-3">
                  <input v-model="loggedAdmin.address" type="text" class="form-control" id="modalAddress" placeholder="Address">
                  <label for="modalAddress">Address</label>
                </div>
                <div class="form-floating mb-3">
                  <input v-model="loggedAdmin.city" type="text" class="form-control" id="modalCity" placeholder="Place">
                  <label for="modalCity">Place</label>
                </div>
                <div class="form-floating mb-3">
                  <country-select v-model="loggedAdmin.country" class="form-control" id="modalCountry" :usei18n="false" :autocomplete="true"  :countryName="true"/>
                  <label for="modalCountry">Country</label>
                </div>
                <div class="form-floating mb-3">
                  <vue-tel-input id="phoneNumInput" class="form-control" v-model="loggedAdmin.phoneNum" @validate="telValidate"></vue-tel-input>
                  <label for="phoneNumInput">Phone Number</label>
                </div>
              </div>
              <div class="modal-footer">
                <button type="button" class="custom-btn button-primary" data-bs-dismiss="modal">Close</button>
                <button type="button" @click="saveInfoEdit" class="custom-btn button-primary" data-bs-dismiss="modal">Save Changes</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="tab-pane fade" id="nav-entities" role="tabpanel" aria-labelledby="nav-entities-tab">Entities</div>
    <div class="tab-pane fade" id="nav-reviews" role="tabpanel" aria-labelledby="nav-reviews-tab">
      <router-view></router-view>
    </div>
    <div class="tab-pane fade" id="nav-complaints" role="tabpanel" aria-labelledby="nav-complaints-tab">
      <div style="border: 1px solid black; margin: 10px; padding: 10px" v-for="(complaint, index) in this.complaints"
           v-bind:index="index" :key="complaint.id">
        <div>Text: {{ complaint.text }}</div>
        <div>Approved: {{ complaint.approved }}</div>
        <div>
          <button class="custom-btn button-primary" @click="approveReport(complaint, index)">Approve Complaint</button>
        </div>
      </div>
    </div>
    <div class="tab-pane fade" id="nav-requests" role="tabpanel" aria-labelledby="nav-requests-tab">
      <div style="border: 1px solid black; margin: 10px; padding: 10px" v-for="(request, index) in this.requests"
           v-bind:index="index" :key="request.id">
        <div>Text: {{ request.text }}</div>
        <div>Type: {{ request.type }}</div>
        <div>
          <button class="custom-btn button-primary" @click="approveRequest(request, index)">Approve Request</button>
        </div>
      </div>
    </div>
    <div class="tab-pane fade" id="nav-reports" role="tabpanel" aria-labelledby="nav-reports-tab">...</div>
    <div class="tab-pane fade" id="nav-register" role="tabpanel" aria-labelledby="nav-register-tab">
      <div style="text-align: center; width: 50%">
        <div class="w-100">
          <div class="form-floating input-group-width mb-2">
            <input id="firstNameInput" class="form-control" type="text" v-model="firstName" placeholder="First Name">
            <label for="firstNameInput">First Name</label>
          </div>
          <div class="form-floating input-group-width mb-2">
            <input id="lastNameInput" class="form-control" type="text" v-model="lastName" placeholder="Last Name">
            <label for="lastNameInput">Last Name</label>
          </div>
          <div class="form-floating input-group-width mb-2">
            <input id="emailInput" class="form-control" type="text" v-model="email" @blur="validateEmail"
                   placeholder="E-mail">
            <label for="emailInput">E-mail</label>
          </div>
          <div class="form-floating input-group-width mb-2">
            <input id="usernameInput" class="form-control" type="text" v-model="username" placeholder="Username">
            <label for="usernameInput">Username</label>
          </div>
          <div class="form-floating input-group-width mb-2">
            <input id="passwordInput" class="form-control" type="password" v-model="password" placeholder="Password">
            <label for="passwordInput">Password</label>
          </div>
          <div class="form-floating input-group-width mb-2">
            <input id="confirmPasswordInput" class="form-control" type="password" v-model="confirmPassword"
                   placeholder="Confirm Password">
            <label for="confirmPasswordInput">Confirm Password</label>
          </div>
          <div class="form-floating input-group-width mb-2">
            <input id="addressInput" class="form-control" type="text" v-model="address" placeholder="Address">
            <label for="addressInput">Address</label>
          </div>
          <div class="form-floating input-group-width mb-2">
            <input id="cityInput" class="form-control" type="text" v-model="city" placeholder="City">
            <label for="cityInput">City</label>
          </div>
          <div class="form-floating input-group-width mb-2">
            <country-select id="countryInput" class="form-control" v-model="country" :usei18n="false"
                            :autocomplete="true"
                            :countryName="true"/>
            <label for="countryInput">Country</label>
          </div>
          <div class="form-floating input-group-width mb-2">
            <vue-tel-input id="phoneNumInput" class="form-control" v-model="phoneNum"
                           @validate="telValidate"></vue-tel-input>
            <label for="phoneNumInput">Phone Number</label>
          </div>
          <div>
            <button class="custom-btn button-primary" @click="addAdmin">Sign up</button>
          </div>
        </div>
      </div>
    </div>
    <div class="tab-pane fade" id="nav-fee" role="tabpanel" aria-labelledby="nav-fee-tab">
      <input v-model="fee"/>
      <button class="custom-btn button-primary" @click="saveFee">Save fee</button>
    </div>
    <div class="tab-pane fade" id="nav-reservations" role="tabpanel" aria-labelledby="nav-reservations-tab">
      <div style="border: 1px solid black; margin: 10px; padding: 10px"
           v-for="(reservation, index) in this.reservations"
           v-bind:index="index" :key="reservation.id">
        <div>Name: {{ reservation.rentingEntityName }}</div>
        <div>Price: {{ reservation.price }}</div>
        <div>Fee: {{ reservation.fee }}</div>
        <div>Date: {{ reservation.start }}</div>
        <div>Duration: {{ reservation.duration }}</div>
      </div>
    </div>
    <div class="tab-pane fade" id="nav-loyalty" role="tabpanel" aria-labelledby="nav-loyalty-tab">...</div>
  </div>
</template>

<script>

import axios from "axios"

export default {
  name: "AdminProfile",
  data() {
    return {
      loggedAdmin: {},
      profilePicture: null,
      selectedFile: null,
      complaints: [],
      requests: [],
      reservations: [],
      fee: null,


      firstName: '',
      lastName: '',
      username: '',
      email: '',
      password: '',
      confirmPassword: '',
      address: '',
      city: '',
      country: '',
      validNumber: '',
      phoneNum: null,
    }
  },

  mounted() {
    axios.get("api/users/loggedUser", {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => (this.loggedAdmin = response.data))
    axios.get("api/users/loggedUser/picture", {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => (this.profilePicture = response.data))
    axios.get("api/admins/complaints/all", {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => (this.complaints = response.data))
    axios.get("api/admins/requests/all", {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => (this.requests = response.data))
    axios.get("api/admins/reservations/all", {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => (this.reservations = response.data))
    axios.get("api/admins/fee", {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => (this.fee = response.data))

  },

  methods: {
    approveReport(complaint, index) {
      axios.put("api/admins/complaints", complaint, {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => {
        if (response.data == true) {
          this.$toast.success("Approved");
          this.complaints[index].approved = true;
        } else {
          this.$toast.error("Problem has occurred!");
        }
      });
    },

    approveRequest(request, index) {
      axios.put("api/admins/requests", request, {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => {
        if (response.data == true) {
          this.$toast.success("Deleted");
          this.requests[index].deleted = true;
        } else {
          this.$toast.error("Problem has occurred!");
        }
      });
    },

    telValidate(phoneNum) {
      if (phoneNum.valid) {
        this.validNumber = phoneNum.number;
      } else {
        this.validNumber = '';
      }
    },

    validateEmail(email) {
      var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return re.test(email);
    },

    saveFee() {
      const feeDTO = {id: 1, value: this.fee};
      axios.put("api/admins/fee", feeDTO, {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => {
        if (response.data == true) {
          this.$toast.success("Successfully saved!");
        } else {
          this.$toast.error("Problem has occurred!");
        }
      });
    },

    addAdmin() {
      var regExp = /^[A-Za-z]+$/;

      if (this.firstName.length < 2 || this.firstName.length > 20) {
        this.$toast.error("First name must be between 2-20 characters");
        return;
      }
      if (regExp.test(this.firstName) == false) {
        this.$toast.error("First name must contain only letters");
        return;
      }
      this.firstName = this.firstName.charAt(0).toUpperCase() + this.firstName.slice(1);

      if (this.lastName.length < 2 || this.lastName.length > 20) {
        this.$toast.error("Last name must be between 2-20 characters");
        return;
      }
      if (regExp.test(this.lastName) == false) {
        this.$toast.error("Last name must contain only letters");
        return;
      }
      this.lastName = this.lastName.charAt(0).toUpperCase() + this.lastName.slice(1);
      if (this.email.length < 5 || this.validateEmail(this.email) == false) {
        this.$toast.error("Invalid email");
        return;
      }
      this.email.toLowerCase();

      var passwordRegex = /^[0-9A-Za-z]+$/;

      if (this.username.length < 3 || this.username.length > 20) {
        this.$toast.error("Username must be between 3-20 characters");
        return;
      }
      if (!passwordRegex.test(this.username)) {
        this.$toast.error("Username must only contain letters and numbers");
        return;
      }

      if (this.password.length < 5 || this.password.length > 20) {
        this.$toast.error("Password must be between 5-20 characters");
        return;
      }
      if (!passwordRegex.test(this.password)) {
        this.$toast.error("Password must only contain letters and numbers");
        return;
      }
      if (this.password !== this.confirmPassword) {
        this.$toast.error("Passwords must match");
        return;
      }
      if (this.address === '') {
        this.$toast.error("Provide address");
        return;
      }
      if (this.city === '') {
        this.$toast.error("Provide city name");
        return;
      }

      if (this.country == "") {
        this.$toast.error("Provide country");
        return;
      }

      if (this.validNumber === '') {
        this.$toast.error("Phone number invalid");
        return;
      }

      axios.get(`api/auth/isMailAvailable/${this.email}`).then(response => {
        if (response.data == false) {
          this.$toast.error("Email already taken");
          return;
        } else {

          axios.get(`api/auth/isUsernameAvailable/${this.username}`).then(response => {

            if (response.data == false) {
              this.$toast.error("Username already taken");
              return;
            } else {
              const admin = {
                'firstName': this.firstName,
                'lastName': this.lastName,
                'username': this.username,
                'email': this.email,
                'password': this.password,
                "confirmPassword": this.confirmPassword,
                'address': this.address,
                'city': this.city,
                'country': this.country,
                'phoneNum': this.validNumber
              };
              axios.post("/api/auth/addAdmin", admin).then(response => {
                if (response.data == false) {
                  this.$toast.error("Registration failed");
                } else {
                  this.$toast.success("Registration successful");
                }
              });
            }
          })
        }
      });
    },

    changePicture(event) {
      this.selectedFile = event.target.files[0];
      if (this.selectedFile == null) {
        this.$toast.info("Select a file first!");
      } else {
        if (this.selectedFile.size > 1048576) {
          this.$toast.info("File is too large!\nMust be smaller than 1MB.");
        } else {
          const formData = new FormData();
          formData.append('image', this.selectedFile, this.selectedFile.name);
          axios.put("/api/users/loggedUser/picture", formData, {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => (this.profilePicture = response.data))
        }
      }
    }



  }
}
</script>

<style scoped>

</style>
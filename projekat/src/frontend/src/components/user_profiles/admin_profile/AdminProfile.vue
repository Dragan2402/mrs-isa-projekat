<template>
  <div style="display: flex; justify-content: center; ">
    <div style="display: inline-flex; border: 1px solid #00587a; border-radius: 5px;">
      <div>
        <img v-bind:src="'data:image/jpeg;base64,' + profilePicture" alt="Profile picture"
             style="width: 300px; height: 300px; object-fit: cover; border-radius: 3px">
      </div>
      <div class="info-wrapper" style="display: inline; margin: auto 50px; text-align: left">
        <div><b>First Name: </b> {{ loggedAdmin.firstName }}</div>
        <div><b>Last Name: </b> {{ loggedAdmin.lastName }}</div>
        <div><b>E-mail: </b> {{ loggedAdmin.email }}</div>
        <div><b>Address: </b> {{ loggedAdmin.address }}</div>
        <div><b>Place: </b> {{ loggedAdmin.city }}</div>
        <div><b>Country: </b> {{ loggedAdmin.country }}</div>
        <div><b>Phone Number: </b> {{ loggedAdmin.phoneNum }}</div>
      </div>
    <div>
      <button type="button" @click="backupData" class="custom-btn button-secondary" data-bs-toggle="modal"
              data-bs-target="#infoModal"><i class="bi bi-pencil-square"></i></button>
    </div>
    </div>
  </div>


    <div class="modal fade" id="infoModal" tabindex="-1" aria-hidden="true">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-body">
            <img id="profile-pic" v-bind:src="'data:image/jpeg;base64,' + profilePicture" alt="Profile picture"
                 style="width: 200px; height: 200px; object-fit: cover; border-radius: 5px">
            <div style="margin: 10px 0 10px 120px">
              <input style="cursor: pointer" type="file" @change="changePicture" accept="image/png, image/jpeg">
            </div>
            <div class="form-floating mb-3">
              <input v-model="loggedAdmin.firstName" type="text" class="form-control" id="modalFirstName"
                     placeholder="First Name">
              <label for="modalFirstName">First Name</label>
            </div>
            <div class="form-floating mb-3">
              <input v-model="loggedAdmin.lastName" type="text" class="form-control" id="modalLastName"
                     placeholder="Last Name">
              <label for="modalLastName">Last Name</label>
            </div>
            <div class="form-floating mb-3">
              <input v-model="loggedAdmin.email" type="email" class="form-control" id="modalEmail"
                     placeholder="E-mail">
              <label for="modalEmail">E-mail</label>
            </div>
            <div class="form-floating mb-3">
              <input v-model="loggedAdmin.address" type="text" class="form-control" id="modalAddress"
                     placeholder="Address">
              <label for="modalAddress">Address</label>
            </div>
            <div class="form-floating mb-3">
              <input v-model="loggedAdmin.city" type="text" class="form-control" id="modalCity" placeholder="Place">
              <label for="modalCity">Place</label>
            </div>
            <div class="form-floating mb-3">
              <input v-model="loggedAdmin.country" class="form-control" id="modalCountry"/>
              <label for="modalCountry">Country</label>
            </div>
            <div class="form-floating mb-3">
              <input id="phoneNumInput" class="form-control" v-model="loggedAdmin.phoneNum"/>
              <label for="phoneNumInput">Phone Number</label>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" @click="getBackup" class="custom-btn button-primary" data-bs-dismiss="modal">Close
            </button>
            <button type="button" @click="saveInfoEdit" class="custom-btn button-primary" data-bs-dismiss="modal">
              Save Changes
            </button>
          </div>
        </div>
      </div>
    </div>
</template>

<script>
import axios from "axios";

export default {
  name: "AdminProfile",
  data() {
    return {
      loggedAdmin: {},
      backupAdmin: {},
      profilePicture: null,
      selectedPicture: null,
      changingPicture: false
    }
  },
  mounted() {
    axios.get("https://renting-buddy-spring.herokuapp.com/api/admins/complaints/all", {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => (this.complaints = response.data))
    axios.get("https://renting-buddy-spring.herokuapp.com/api/admins/reviews/all", {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => (this.reviews = response.data))
    axios.get("https://renting-buddy-spring.herokuapp.com/api/admins/requests/all", {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => (this.requests = response.data))

    axios.get("https://renting-buddy-spring.herokuapp.com/api/users/loggedUser", {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => (this.loggedAdmin = response.data))
    axios.get("https://renting-buddy-spring.herokuapp.com/api/users/loggedUser/picture", {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => (this.profilePicture = response.data))
  },
  methods: {
    approveReport(complaint, index) {
      axios.put("https://renting-buddy-spring.herokuapp.com/api/admins/complaints", complaint, {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => {
        if (response.data == true) {
          this.$toast.success("Approved");
          this.complaints[index].approved = true;
        } else {
          this.$toast.error("Problem has occurred!");
        }
      });
    },

    approveReview(review, index) {
      axios.put("https://renting-buddy-spring.herokuapp.com/api/admins/reviews", review, {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => {
        if (response.data == true) {
          this.$toast.success("Approved");
          this.reviews[index].approved = true;
        } else {
          this.$toast.error("Problem has occurred!");
        }
      });
    },

    approveRequest(request, index) {
      axios.put("https://renting-buddy-spring.herokuapp.com/api/admins/requests", request, {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => {
        if (response.data == true) {
          this.$toast.success("Deleted");
          this.requests[index].deleted = true;}
  })},

    backupData() {
      this.backupAdmin = {
        firstName: this.loggedAdmin.firstName,
        lastName: this.loggedAdmin.lastName,
        email: this.loggedAdmin.email,
        address: this.loggedAdmin.address,
        city: this.loggedAdmin.city,
        country: this.loggedAdmin.country,
        phoneNum: this.loggedAdmin.phoneNum
      };
    },

    getBackup() {
      this.loggedAdmin = {
        firstName: this.backupAdmin.firstName,
        lastName: this.backupAdmin.lastName,
        email: this.backupAdmin.email,
        address: this.backupAdmin.address,
        city: this.backupAdmin.city,
        country: this.backupAdmin.country,
        phoneNum: this.backupAdmin.phoneNum
      };
    },

    changePicture(event) {
      this.selectedPicture = event.target.files[0];
      this.changingPicture = true;
    },

    saveInfoEdit() {
      const regExp = /^[A-Za-z]+$/;

      if (this.loggedAdmin.firstName.length < 2 || this.loggedAdmin.firstName.length > 20) {
        this.$toast.error("First name must be between 2-20 characters");
        this.getBackup();
        return;
      }
      if (regExp.test(this.loggedAdmin.firstName) == false) {
        this.$toast.error("First name must contain only letters");
        this.getBackup();
        return;
      }

      this.loggedAdmin.firstName = this.loggedAdmin.firstName.charAt(0).toUpperCase() + this.loggedAdmin.firstName.slice(1);

      if (this.loggedAdmin.lastName.length < 2 || this.loggedAdmin.lastName.length > 20) {
        this.$toast.error("Last name must be between 2-20 characters");
        this.getBackup();
        return;
      }
      if (regExp.test(this.loggedAdmin.lastName) == false) {
        this.$toast.error("Last name must contain only letters");
        this.getBackup();
        return;
      }

      this.loggedAdmin.lastName = this.loggedAdmin.lastName.charAt(0).toUpperCase() + this.loggedAdmin.lastName.slice(1);

      if (this.loggedAdmin.email === '') {
        this.$toast.error("Provide email");
        this.getBackup();
        return;
      }

      if (this.loggedAdmin.address === '') {
        this.$toast.error("Provide address");
        this.getBackup();
        return;
      }
      if (this.loggedAdmin.city === '') {
        this.$toast.error("Provide city name");
        this.getBackup();
        return;
      }

      if (this.loggedAdmin.country == "") {
        this.$toast.error("Provide country");
        this.getBackup();
        return;
      }

      if (this.loggedAdmin.phoneNum === '') {
        this.$toast.error("Phone number invalid");
        this.getBackup();
        return;
      }

      if (this.changingPicture && this.selectedPicture.size > 1048576){
        this.$toast.error("Picture must be smaller than 1MB.");
        this.getBackup();
        return
      }

      if (this.changingPicture === true) {
        const formDataPicture = new FormData();
        formDataPicture.append('image', this.selectedPicture, this.selectedPicture.name);
        axios.put("https://renting-buddy-spring.herokuapp.com/api/users/loggedUser/picture", formDataPicture, {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => (this.profilePicture = response.data))
      }
      axios.put("https://renting-buddy-spring.herokuapp.com/api/admins/loggedAdmin", this.loggedAdmin, {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => {
        if (response.data == false) {
          this.$toast.error("Edit failed");
        } else {
          this.$toast.success("Edit successful");
        }
      });
    },
  }
}
</script>

<style scoped>

.info-wrapper div {
  padding-bottom: 10px;
}

input[type=file]::file-selector-button {
  display: inline-block;
  font-weight: 400;
  text-align: center;
  white-space: nowrap;
  vertical-align: middle;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  border: 1px solid white;
  padding: 0.375rem 0.75rem;
  font-size: 1rem;
  line-height: 1.5;
  border-radius: 0.25rem;
  transition: color 0.3s ease-in-out, background-color 0.3s ease-in-out, border-color 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
  color: white;
  background-color: #00587a;
  cursor: pointer;
}

</style>
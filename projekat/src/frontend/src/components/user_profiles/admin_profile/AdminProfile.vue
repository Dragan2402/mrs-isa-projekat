<template>
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
      <button type="button" class="custom-btn button-primary" data-bs-toggle="modal" data-bs-target="#infoModal">
        Change Info
      </button>
    </div>


    <div class="modal fade" id="infoModal" tabindex="-1" aria-hidden="true">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <div class="modal-body">
            <img v-bind:src="'data:image/jpeg;base64,' + profilePicture" alt="Profile picture"
                 style="width: 200px; height: 200px">
            <div>
              <input type="file" @change="changePicture" accept="image/png, image/jpeg">
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
              <country-select v-model="loggedAdmin.country" class="form-control" id="modalCountry" :usei18n="false"
                              :autocomplete="true" :countryName="true"/>
              <label for="modalCountry">Country</label>
            </div>
            <div class="form-floating mb-3">
              <vue-tel-input id="phoneNumInput" class="form-control" v-model="loggedAdmin.phoneNum"
                             @validate="telValidate"></vue-tel-input>
              <label for="phoneNumInput">Phone Number</label>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="custom-btn button-primary" data-bs-dismiss="modal">Close</button>
            <button type="button" @click="saveInfoEdit" class="custom-btn button-primary" data-bs-dismiss="modal">
              Save Changes
            </button>
          </div>
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
      profilePicture: null,
      selectedFile: null
    }
  },
  mounted() {
    axios.get("api/users/loggedUser", {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => (this.loggedAdmin = response.data))
    axios.get("api/users/loggedUser/picture", {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => (this.profilePicture = response.data))
  },
  methods: {
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
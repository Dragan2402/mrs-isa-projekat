<template>
<!--  <h3>Profile Information</h3>-->
  <div class="main-description">
    <div class="picture-container me-5 text-center">
      <img class="picture rounded-3 mb-3" v-bind:src="'data:image/jpeg;base64,' + picture" alt="Profile picture">

    </div>
    <div class="inner-description">
      <div><b>First Name: </b> {{user.firstName}}</div>
      <div><b>Last Name: </b> {{user.lastName}}</div>
      <div><b>E-mail: </b> {{user.email}}</div>
      <div><b>Address: </b> {{user.address}}</div>
      <div><b>Place: </b> {{user.city}}</div>
      <div><b>Country: </b> {{user.country}}</div>
      <div><b>Phone Number: </b> {{user.phoneNum}}</div>
    </div>
  </div>

  <div class="modal-button-div">
    <button type="button" class="custom-btn button-primary me-5" data-bs-toggle="modal" data-bs-target="#pictureModal">Change Picture</button>
    <button type="button" class="custom-btn button-primary" data-bs-toggle="modal" data-bs-target="#infoModal">Change Info</button>
  </div>

  <div class="modal fade" id="infoModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-body">
          <div class="form-floating mb-3">
            <input v-model="user.firstName" type="text" class="form-control" id="modalFirstName" placeholder="First Name">
            <label for="modalFirstName">First Name</label>
          </div>
          <div class="form-floating mb-3">
            <input v-model="user.lastName" type="text" class="form-control" id="modalLastName" placeholder="Last Name">
            <label for="modalLastName">Last Name</label>
          </div>
          <div class="form-floating mb-3">
            <input v-model="user.email" type="email" class="form-control" id="modalEmail" placeholder="E-mail">
            <label for="modalEmail">E-mail</label>
          </div>
          <div class="form-floating mb-3">
            <input v-model="user.address" type="text" class="form-control" id="modalAddress" placeholder="Address">
            <label for="modalAddress">Address</label>
          </div>
          <div class="form-floating mb-3">
            <input v-model="user.city" type="text" class="form-control" id="modalCity" placeholder="Place">
            <label for="modalCity">Place</label>
          </div>
          <div class="form-floating mb-3">
            <country-select v-model="user.country" class="form-control" id="modalCountry" :usei18n="false" :autocomplete="true"  :countryName="true"/>
            <label for="modalCountry">Country</label>
          </div>
          <div class="form-floating mb-3">
            <vue-tel-input id="phoneNumInput" class="form-control" v-model="phoneNum" @validate="telValidate"></vue-tel-input>
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
</template>

<script>
import {useRouter} from "vue-router";
import {getCurrentInstance, onMounted, ref} from "vue";
import axios from "axios";

export default {
  name: "VacationHouseOwnerInfo",
  setup() {
    const router = useRouter();
    const root = getCurrentInstance();

    let user = ref({
      id: null,
      email: null,
      username: null,
      firstName: null,
      lastName: null,
      address: null,
      city: null,
      country: null,
      phoneNum: null,
      penalties: null,
      accountType: null
    });
    let phoneNum = ref(null);
    let validatedPhoneNum = ref("")
    let picture = ref("");
    let page = ref("homePage");

    onMounted(() => {
      axios
          .get("/api/vacationHouseOwners/loggedVacationHouseOwner",
              { headers: { "Authorization" : `Bearer ${localStorage.getItem("jwt")}`}})
          .then(response => {
            user.value = response.data;
          })
          .catch(error => {
            alert(error.response);
            root.accessToken = null;
            localStorage.setItem("jwt", null);
            router.push("/loginPage");
          });
      axios
          .get("/api/users/loggedUser/picture",
              { headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`}})
          .then(response => (picture.value = response.data))
          .catch((error) => {
            alert(error.response);
            root.accessToken = null;
            localStorage.setItem("jwt", null);
            router.push("/loginPage");
          });
    });

    function telValidate(phoneNumber) {
      if (phoneNumber.valid) {
        validatedPhoneNum.value = phoneNumber.number;
      } else {
        validatedPhoneNum.value = '';
      }
    }

    function saveInfoEdit(){
      if (validatedPhoneNum.value !== "")
        user.value.phoneNum = validatedPhoneNum.value;

      axios
          .put(`/api/vacationHouseOwners/${user.value.id}`, user.value,
              { headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} })
          .then(response => {
            user.value = response.data;
            alert("Info updated");
          })
          .catch(error => {
            alert(error.response);
          });
    }

    return {
      user,
      phoneNum,
      picture,
      page,
      telValidate,
      saveInfoEdit
    }
  }
}
</script>

<style scoped>

.main-description {
  display:flex;
  margin-top: 17px;
  padding: 0;
}

.inner-description {
  margin-right: 50px;
  float: left;
  width: 65%;
  overflow: hidden;
}

.picture-container {
  float: left;
  width: 11em;
}

.picture {
  border: 2px solid #00587a;
  width: 11em;
  height: 11em;
  overflow: hidden;
}

.modal-button-div {
  float: left;
}

.modal-button-div button {
  width: 11em;
}

</style>
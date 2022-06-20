<template>
<!--  <h3>Profile Information</h3>-->
  <div class="main-description">
    <div class="picture-container me-5 text-center">
      <img class="picture rounded-3 mb-3" v-bind:src="'data:image/jpeg;base64,' + picture" alt="Profile picture">

    </div>
    <div class="inner-description">
      <div><b>First Name: </b> {{owner.firstName}}</div>
      <div><b>Last Name: </b> {{owner.lastName}}</div>
      <div><b>E-mail: </b> {{owner.email}}</div>
      <div><b>Address: </b> {{owner.address}}</div>
      <div><b>Place: </b> {{owner.city}}</div>
      <div><b>Country: </b> {{owner.country}}</div>
      <div><b>Phone Number: </b> {{owner.phoneNum}}</div>
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
            <input v-model="owner.firstName" type="text" class="form-control" id="modalFirstName" placeholder="First Name">
            <label for="modalFirstName">First Name</label>
          </div>
          <div class="form-floating mb-3">
            <input v-model="owner.lastName" type="text" class="form-control" id="modalLastName" placeholder="Last Name">
            <label for="modalLastName">Last Name</label>
          </div>
          <div class="form-floating mb-3">
            <input v-model="owner.email" type="email" class="form-control" id="modalEmail" placeholder="E-mail">
            <label for="modalEmail">E-mail</label>
          </div>
          <div class="form-floating mb-3">
            <input v-model="owner.address" type="text" class="form-control" id="modalAddress" placeholder="Address">
            <label for="modalAddress">Address</label>
          </div>
          <div class="form-floating mb-3">
            <input v-model="owner.city" type="text" class="form-control" id="modalCity" placeholder="Place">
            <label for="modalCity">Place</label>
          </div>
          <div class="form-floating mb-3">
            <country-select v-model="owner.country" class="form-control" id="modalCountry" :usei18n="false" :autocomplete="true"  :countryName="true"/>
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
import {onMounted, ref} from "vue";
import {useRouter} from "vue-router";
import axios from "axios";

export default {
  name: "OwnerInfo",
  setup() {
    const router = useRouter();
    // const root = getCurrentInstance();

    let entityTypeInfo = ref({
      urlPart: ""
    });

    let owner = ref({
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
          .get("https://renting-buddy-spring.herokuapp.com/api/users/loggedUser",
              { headers: { "Authorization" : `Bearer ${localStorage.getItem("jwt")}`}})
          .then(response => {
            owner.value = response.data;

            if(response.data.accountType === "VH_OWNER") {
              entityTypeInfo.value = {
                urlPart: "vacationHouseOwners"
              };

            } else if (response.data.accountType === "SH_OWNER") {
              entityTypeInfo.value = {
                urlPart: "shipOwners"
              };

              // else if INSTRUCTOR, ADMIN

            } else {
              router.push({name: "homePage"});
            }
          })
          .catch(error => {
            console.log(error.response);
            // root.accessToken = null;
            // localStorage.setItem("jwt", null);
            // router.push("/loginPage");
          });
      axios
          .get("https://renting-buddy-spring.herokuapp.com/api/users/loggedUser/picture",
              { headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`}})
          .then(response => (picture.value = response.data))
          .catch((error) => {
            console.log(error.response);
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
        owner.value.phoneNum = validatedPhoneNum.value;

      axios
          .put(`https://renting-buddy-spring.herokuapp.com/api/${entityTypeInfo.value.urlPart}/${owner.value.id}`, owner.value,
              { headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} })
          .then(response => {
            owner.value = response.data;
            console.log("Info updated");
          })
          .catch(error => {
            console.log(error.response);
          });
    }

    return {
      owner,
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
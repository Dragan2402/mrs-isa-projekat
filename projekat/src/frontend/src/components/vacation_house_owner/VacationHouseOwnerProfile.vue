<template>
  <div class="main-container">
    <div style="display: flex" class="mb-3">
      <div style="float: left; width: 70%">
        <h3 class="name-heading">{{firstName}} {{lastName}}</h3>
        <h5 class="username-heading">@{{username}}</h5>
      </div>
      <div style="width: 30%; margin-top: 20px">
        <div style="float: right; display: flex; overflow: hidden;">
          <div>
            <input class="radio" type="radio" id="homePageRadio" name="page" value="homePage" v-model="page" @change="routePage">
            <label class="custom-btn button-primary me-3" for="homePageRadio">Vacation Houses</label>
          </div>
          <div>
            <input class="radio" type="radio" id="infoPageRadio" name="page" value="infoPage" v-model="page" @change="routePage">
            <label class="custom-btn button-primary " for="infoPageRadio">Profile Information</label>
          </div>
        </div>
      </div>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>

import {useRouter} from "vue-router";
import {ref, getCurrentInstance, onMounted} from "vue";
import axios from "axios";

export default {
  name: "VacationHouseOwnerProfile",
  setup() {
    const router = useRouter();
    const root = getCurrentInstance();

    let firstName = ref("");
    let lastName = ref("");
    let username = ref("");
    let page = ref("homePage");

    onMounted(() => {
      axios
          .get("/api/vacationHouseOwners/loggedVacationHouseOwner",
              { headers: { "Authorization" : `Bearer ${localStorage.getItem("jwt")}`}})
          .then(response => {
            firstName.value = response.data.firstName;
            lastName.value = response.data.lastName;
            username.value = response.data.username;
          })
          .catch(error => {
            console.log(error.response);
            root.accessToken = null;
            localStorage.setItem("jwt", null);
            router.push("/loginPage");
          });
      // axios
      //     .get("/api/users/loggedUser/picture",
      //         { headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`}})
      //     .then(response => (picture.value = response.data))
      //     .catch((error) => {
      //       alert(error.response);
      //       root.accessToken = null;
      //       localStorage.setItem("jwt", null);
      //       router.push("/loginPage");
      //     });

      page.value = "homePage";
      routePage();
    });

    function routePage() {
      if(page.value === "homePage")
        router.push({name: "vacationHouseOwnerHome"});
      else if(page.value === "infoPage")
        router.push({name: "vacationHouseOwnerInfo"});
    }

    return {
      firstName,
      lastName,
      username,
      page,
      routePage
    }
  }
}

</script>

<style scoped>

/*.main-container {*/
/*  width: 60%;*/
/*  margin: auto auto 20% auto;*/
/*  min-width: 860px;*/
/*}*/

.name-heading {
  font-weight: bold;
  /*margin-top: 1rem;*/
}

.username-heading {
  margin-top: 3px;
}

.radio {
  opacity: 0;
  width: 0;
  height: 0;
}

[type=radio]:checked + label {
  background-color: white;
  border-color: #00587a;
  color: #00587a;
}

</style>
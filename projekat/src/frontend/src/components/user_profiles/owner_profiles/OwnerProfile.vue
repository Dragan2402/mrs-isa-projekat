<template>
  <div class="main-container">
    <div style="display: flex" class="mb-3">
      <div style="float: left; width: 70%">
        <h3 class="name-heading">{{owner.firstName}} {{owner.lastName}}</h3>
        <h5 class="username-heading">@{{owner.username}}</h5>
      </div>
      <div style="width: 30%; margin-top: 20px">
        <div style="float: right; display: flex; overflow: hidden;">
          <div>
            <input class="radio" type="radio" id="homePageRadio" name="page" value="homePage" v-model="page" @change="routePage">
            <label class="custom-btn button-primary me-3" for="homePageRadio">{{buttonLabel}}</label>
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
import {ref, onMounted} from "vue";
import axios from "axios";

export default {
  name: "OwnerProfile",
  setup() {
    const router = useRouter();

    let owner = ref({});
    let buttonLabel = ref("");
    let page = ref("homePage");

    onMounted(() => {
      axios.get("/api/users/loggedUser",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} })
          .then(response => {
            owner.value = response.data;

            if(response.data.accountType === "VH_OWNER") {
              buttonLabel.value = "Vacation Houses";

            } else if (response.data.accountType === "SH_OWNER") {
              buttonLabel.value = "Ships";

            // else if INSTRUCTOR

            } else {
              router.push({name: "homePage"});
            }

            page.value = "homePage";
            routePage();
          });
    });

    function routePage() {
      if(page.value === "homePage")
        router.push({name: "ownerHome"});
      else if(page.value === "infoPage")
        router.push({name: "ownerInfo"});
    }

    return {
      owner,
      buttonLabel,
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
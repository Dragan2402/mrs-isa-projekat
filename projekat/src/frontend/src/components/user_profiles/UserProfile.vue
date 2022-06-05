<template>
  <router-view></router-view>
</template>

<script>
import {onMounted} from "vue";
import {useRouter} from "vue-router";
import axios from "axios";

export default {
  name: "UserProfile",
  setup() {
    const router = useRouter();

    onMounted(() => {
      axios.get("/api/users/loggedUser",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} })
          .then(response => {
            if(response.data.accountType === "VH_OWNER") {
              router.push({name: "vacationHouseOwnerHome"});
            } else if (response.data.accountType === "SH_OWNER") {
              console.log(`UserProfile push: ${response.data.accountType}`);
              router.push({name: "shipOwnerHome"});
            }
          });
    })
  }
}
</script>

<style scoped>

</style>
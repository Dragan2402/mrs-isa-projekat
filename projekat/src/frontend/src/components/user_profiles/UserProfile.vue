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
            if(response.data.accountType === "CLIENT") {
              router.push({name: "clientProfile"});

            } else if (response.data.accountType === undefined ||
                       response.data.accountType === null ||
                       response.data.accountType === "") {
              router.push({name: "loginPage"});

            } else {
              // console.log(`UserProfile push: ${response.data.accountType}`);
              router.push({name: "ownerHome"});
            }
            // ADMIN?
          });
    })
  }
}
</script>

<style scoped>

</style>
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
      console.log("userProfile onMounted")
      axios.get("/api/users/loggedUser",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} })
          .then(response => {
            console.log("userProfile axios then")
            if(response.data.accountType === "CLIENT") {
              console.log("userProfile axios then client")
              router.push({name: "clientProfile"});

            } else if (response.data.accountType === "VH_OWNER" ||
                response.data.accountType === "SH_OWNER" ||
                response.data.accountType === "INSTRUCTOR"){
              // console.log(`UserProfile push: ${response.data.accountType}`);
              console.log("userProfile axios then owner")
              router.push({name: "ownerHome"});
            } else {
              console.log("userProfile axios then login")
              router.push({name: "loginPage"});

            }
            // ADMIN?
          });
    })
  }
}
</script>

<style scoped>

</style>
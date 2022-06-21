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
      axios
          .get("/api/users/loggedUser",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} })
          .then(response => {
            if(response.data.accountType === "CLIENT") {
              router.push({name: "clientProfile"});

            }
            
            else if (response.data.accountType === "VH_OWNER" ||
                response.data.accountType === "SH_OWNER" ||
                response.data.accountType === "INSTRUCTOR"){
              router.push({name: "ownerHome"});

            }

            else if (response.data.accountType === "ADMIN"){
              router.push({name: "adminHome"});
            }
            
            else {
              router.push({name: "loginPage"});
            }
          });
    })
  }
  
}
</script>

<style scoped>

</style>
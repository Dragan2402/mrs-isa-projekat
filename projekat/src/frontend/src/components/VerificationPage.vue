<template>
  <div v-if="verified" align='center'>
    <img src="../assets/verified.png" height="200" width="200">
    <p style="color:green;">Welcome to Renting Buddy</p>
  </div>
  <div v-else align='center'>
    <img src="../assets/confused.png" height="200" width="200">
    <p style="color:red;">Error.....</p>
  </div>

</template>

<script>
import axios from "axios"

export default {
  name: "VerificationPage",
   data(){
    return {
   
      customId:0,
      verified:false,
      

    }
   },
  mounted() {
    this.customId=this.$route.params.customId;
   
    
    axios.get(`https://renting-buddy-spring.herokuapp.com/api/auth/verify/${this.customId}`).then(response => {
      if(response.data==true){
        this.verified=true;
        this.$toast.success("Account successfully verified");    

      }
      else{
        this.verified=false;
        this.$toast.error("Verification failed");
      }
    });
    
  }
}
</script>

<style>

</style>
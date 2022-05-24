<template>
  <div align='center'>
    <br>
    <input type="text" v-model="email" placeholder="E-mail"> <br><br>
    <button class="btn btn-primary" @click="sendResetPasswordMail()">Send reset password email</button>
  </div>
</template>

<script>
import axios from "axios"
export default {
  name: "forgotPasswordPage",
  data: () => {
    return {
      email: "",
     
    };
  },
  mounted(){
    this.$root.signIn= true;
    this.$root.signUp= true;
    this.$root.signedIn=false;
    this.accessToken=null;
    localStorage.setItem("jwt",null);

  },
  methods:{
    sendResetPasswordMail(){
      if (this.email.length<5 || this.validateEmail(this.email)==false) {
         this.$toast.error("Invalid email");
         return;     
      }
      this.email.toLowerCase();
      axios.get(`/api/auth/sendResetPassword/${this.email}`).then(response => {
        
        if(response.data==true){
          this.$toast.success("Reset password mail successfully sent");
          this.$router.push("/");
        }else{
          this.$toast.error("Error");
        }
      })
    },
    validateEmail(email){
      var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;      
      return re.test(email);

    },
  }
}
</script>

<style>


</style>
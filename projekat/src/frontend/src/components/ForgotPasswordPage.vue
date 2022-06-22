<template>

  <div style="display:flex; flex-direction:column;align-items: center;
    justify-content: center;min-height:600px;">
    
    <div style="align-self:center; background-color: #00587a;width:40%;align-items:center;justify-content: center;padding:3%;">
      <div class="form-floating input-group-width mb-4">
          <input id="emailInput" class="form-control" type="text" v-model="email" placeholder="Email">
          <label for="emailInput">Email</label>
      </div>
      <div>
        <button class="custom-btn button-secondary input-group-width mb-3 mt-5" style="margin-left:25%;max-width:350px;" @click="sendResetPasswordMail()">Send reset password email</button>
      </div>
    </div>
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
<template>
  <div align='center'>
    <br>
    <input type="password" v-model="newPassword" placeholder="New password">
    <br><br>
    <input type="password" v-model="confirmPassword" placeholder="Confirm password">
    <br><br>
    <button class="btn btn-primary" @click="setNewPassword()">Set new password</button>
    
  </div>
</template>

<script>
import axios from "axios"
export default {
  name: "newPasswordPage",
  data: () => {
    return {
      newPassword: "",
      confirmPassword: "",
      token:"",
    };
  },
  mounted(){
    this.token=this.$route.params.token;
  },
  methods:{
    setNewPassword(){
      var passwordRegex=/^[0-9A-Za-z]+$/;

      if(this.newPassword.length<5 || this.newPassword.length > 20){
        this.$toast.error("Password must be between 5-20 characters");
        return;
      }
      if(!passwordRegex.test(this.newPassword)){
        this.$toast.error("Password must only contain letters and numbers");
        return;
      }
      if(this.newPassword !== this.confirmPassword){
        this.$toast.error("Passwords must match");
        return;
      }
      const passwordResetDTO={
        "token":this.token,
        "newPassword":this.newPassword,
        "confirmPassword":this.confirmPassword
      }

      axios.put("/api/auth/resetPassword",passwordResetDTO).then(response =>{
        if(response.data==true){
          this.$toast.success("Password changed successfully");
          this.$router.push("/loginPage");
        }else{
          this.$toast.error("Error");
          
        }
      })

      
    }
  }
}
</script>

<style>

</style>
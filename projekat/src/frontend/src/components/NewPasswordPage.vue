<template>
  <div style="display:flex; flex-direction:column;align-items: center;
    justify-content: center;min-height:600px;">    
    <div style="align-self:center; background-color: #00587a;width:40%;align-items:center;justify-content: center;padding:3%;">
      <div class="form-floating input-group-width mb-5">
          <input id="passwordInput" class="form-control" type="password" v-model="newPassword" placeholder="New Password">
          <label for="passwordInput">New Password</label>
      </div>
      <div class="form-floating input-group-width mb-5">
          <input id="passwordInput" class="form-control" type="password" v-model="confirmPassword" placeholder="Confirm Password">
          <label for="passwordInput">Confirm Password</label>
      </div>
      <div>
        <button class="custom-btn button-secondary input-group-width mb-3 mt-5" style="margin-left:35%;max-width:200px;" @click="setNewPassword()">Set new password</button>
      </div>
    </div>
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
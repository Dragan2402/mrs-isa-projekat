<template>
  <div style="display:flex; flex-direction:column;align-items: center;
    justify-content: center;min-height:600px;">
    
    <div style="align-self:center; background-color: #00587a;width:40%;align-items:center;justify-content: center;padding:3%;">
      <div class="form-floating input-group-width mb-4">
          <input id="usernameInput" class="form-control" type="text" v-model="username" placeholder="Username">
          <label for="usernameInput">Username</label>
      </div>
      <div class="form-floating input-group-width mb-5">
          <input id="passwordInput" class="form-control" type="password" v-model="password" placeholder="Password">
          <label for="passwordInput">Password</label>
      </div>
      <div>
        <button class="custom-btn button-secondary input-group-width mb-3 mt-5" style="margin-left:40%;max-width:100px;" @click="login">Login</button>
        <div style="margin-left:35%;">
          <p>
            <router-link to="/forgotPasswordPage">Forgot your password?</router-link>
          </p>
        </div> 
      </div>
    </div>
  </div>
</template>

<script>

import axios from "axios"

export default {
  data: () => {
    return {
      username: "",
      password: "",
      accountType: "",
      firstLogin: null
    };
  },
    mounted(){
    this.$root.signIn=false;
    
  },
  methods:{
    login(){
      var regex=/^[0-9A-Za-z]+$/;
      if(this.username.length<3 || this.username.length > 20){
        this.$toast.error("Username must be between 3-20 characters");
        return;
      }
      if(!regex.test(this.username)){
        this.$toast.error("Username must only contain letters and numbers");
        return;
      }
      if(this.password.length<5 || this.password.length > 20){
        this.$toast.error("Password must be between 5-20 characters");
        return;
      }
      if(!regex.test(this.password)){
        this.$toast.error("Password must only contain letters and numbers");
        return;
      }
      const loginHeaders ={
      'Accept': 'application/json',
      'Content-Type': 'application/json'
      };
      const body = {
      'username': this.username,
      'password': this.password
      };
      
      axios.post("/api/auth/login",body,loginHeaders).then(response =>{        
        this.$root.accessToken=response.data.accessToken;
        this.$root.signedIn=true;
       
        localStorage.setItem("jwt",response.data.accessToken);
        this.$toast.success("Login successfull");   
 
        axios.get("/api/users/loggedUser",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} })
            .then(response => {
              this.$root.loggedUser=response.data
              this.accountType = response.data.accountType;
              this.firstLogin = response.data.firstLogin;
              axios.get("/api/users/loggedUser/picture",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => (this.$root.loggedPicture=response.data));
              if(this.accountType === "ADMIN" && this.firstLogin) this.$router.push({name: "firstLogin"})
              else this.$router.push("/");
            });
      
      }).catch((error) => {
        if(error.response.status === 404)
          this.$toast.error("Account awaiting verification");
        else
          this.$toast.error("Invalid Credentials");
        this.$root.signedIn=false;
        this.$root.accessToken=null;
        localStorage.setItem("jwt",null);
        console.log( error);
      });
    
      
    }

  
  }
};
</script>

<style scoped>

.login-div {
  display:flex; 
  flex-direction:column;
  justify-content:center;
  margin-top: 0;
  min-height:500px;
  padding: 10px;
  border-radius: 5px;
  width: 45%;
  background-color: #00587a;
  align-content: center;
}

</style>
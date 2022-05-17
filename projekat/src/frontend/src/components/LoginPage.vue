<template>
  <div>
    
    <div align="center">
      <input v-model="username" placeholder="username" />
      <br />
      <br />
      <input v-model="password" placeholder="password" type="password" />
      <br />
      <br />
      <button @click="login">Login</button>
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
 
        axios.get("/api/users/loggedUser",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => {this.$root.loggedUser=response.data});
        axios.get("/api/users/loggedUser/picture",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => (this.$root.loggedPicture=response.data));
        this.$router.push("/");  
      
      }).catch((error) => {
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

<style>

</style>
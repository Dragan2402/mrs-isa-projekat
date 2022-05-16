<template>
  <div align='center'>
    <form  @submit.prevent="signUp">
      <div>
        <input type="text" v-model="firstName" placeholder="First Name">
      </div>
      <div>
        <input type="text" v-model="lastName" placeholder="Last Name">
      </div>
      <div>
        <input type="text" v-model="email" placeholder="Email" @blur="validateEmail">
      </div>
      <div>
        <input type="password" v-model="password" placeholder="Password">
      </div>
      <div>
        <input type="password" v-model="confirmPassword" placeholder="Confirm Password">
      </div>
      <div>
        <input type="text" v-model="address" placeholder="Address">
      </div>
      <div>
        <input type="text" v-model="city" placeholder="City">
      </div>
      <div>
        <country-select v-model="country" :usei18n="false" :autocomplete="true"  :countryName="true"/>        
      </div>
      <div>
        <vue-tel-input v-model="phoneNum" @validate="telValidate"></vue-tel-input>
      </div>

      <div>
        <button>SIGN UP</button>
      </div>
    </form>  
  </div>
  
</template>




<script>
import axios from "axios"


export default {
  name: "RegistrationPage",


  data() {
    return{
      
      firstName: '',
      lastName: '',
      email: '',
      password: '',
      confirmPassword: '',
      address: '',
      city: '',
      country: '',
      validNumber: '',
      phoneNum: null
            
    }

  },
  mounted(){
    this.$root.signUp=false;
    this.$root.signIn=false;
  },
  methods:{
    signUp() {
      var regExp = /^[A-Za-z]+$/;
      
      if (this.firstName.length < 2 || this.firstName.length > 20) {
        this.$toast.error("First name must be between 2-20 characters");
        return;
        }
      if(regExp.test(this.firstName)==false){
        this.$toast.error("First name must contain only letters");
        return;
      }
      this.firstName=this.firstName.charAt(0).toUpperCase() + this.firstName.slice(1);
      
      if(this.lastName.length < 2 || this.lastName.length > 20){
        this.$toast.error("Last name must be between 2-20 characters");
        return;
      }
      if(regExp.test(this.lastName)==false){
        this.$toast.error("Last name must contain only letters");
        return;
      }     
      this.lastName=this.lastName.charAt(0).toUpperCase() + this.lastName.slice(1);
      if (this.email.length<5 || this.validateEmail(this.email)==false) {
         this.$toast.error("Invalid email");
         return;     
      }
      this.email.toLowerCase();

      var passwordRegex=/^[0-9A-Za-z]+$/;
      
      if(this.password.length<5 || this.password.length > 20){
        this.$toast.error("Password must be between 5-20 characters");
        return;
      }
      if(!passwordRegex.test(this.password)){
        this.$toast.error("Password must only contain letters and numbers");
        return;
      }
      if(this.password !== this.confirmPassword){
        this.$toast.error("Passwords must match");
        return;
      }
      if(this.address === ''){
        this.$toast.error("Provide address");
        return;
      }
      if(this.city === ''){
        this.$toast.error("Provide city name");
        return;
      }

      if(this.country == ""){
        this.$toast.error("Provide country");
        return;
      }
      
      if(this.validNumber===''){
        this.$toast.error("Phone number invalid");
        return;
      }

    
      
      axios.get(`api/clients/isMailAvailable/${this.email}`).then(response => {
        if(response.data==false){
          this.$toast.error("Email already taken");
          return;
        }
        else{
          const user={'firstName':this.firstName,'lastName':this.lastName,'email':this.email,'password':this.password,"confirmPassword":this.confirmPassword,
          'address':this.address,'city':this.city,'country':this.country,'phoneNum':this.validNumber};
          axios.put("/api/clients/addClient",user).then(response => {
            if(response.data==false){
              this.$toast.error("Registartion failed");
            }else{
              this.$toast.success("Registartion successfull");
              this.$router.push("/");
            }
          });
          
        }
        });
    
            
     
    },
    telValidate(phoneNum) {
      if (phoneNum.valid) {
        this.validNumber = phoneNum.number;
      } else {
        this.validNumber = '';
      } 
    },
    validateEmail(email){
      var re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;      
      return re.test(email);

    }   
    }}
</script>

<style>
form {
  width:400px;
  margin:20px auto;
}
</style>
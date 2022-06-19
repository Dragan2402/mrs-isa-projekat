<template>
  <div align='center'>
    <div class="registration-div mt-3 mb-3">
      <div class="radio-div">
        <div>
          <input class="radio" type="radio" id="addClient" name="choice" value="addClient" v-model="role" checked>
          <label class="custom-btn button-primary input-group-width mb-2 mt-5" for="addClient">Client</label>
        </div>
        <div>
          <input class="radio" type="radio" id="addVacationHouseOwner" name="choice" value="addVacationHouseOwner" v-model="role">
          <label class="custom-btn button-primary input-group-width mb-2" for="addVacationHouseOwner">Vacation House Owner</label>
        </div>
        <div>
          <input class="radio" type="radio" id="addShipOwner" name="choice" value="addShipOwner" v-model="role">
          <label class="custom-btn button-primary input-group-width mb-2" for="addShipOwner">Ship Owner</label>
        </div>
        <div>
          <input class="radio" type="radio" id="addFishingInstructor" name="choice" value="addFishingInstructor" v-model="role">
          <label class="custom-btn button-primary input-group-width mb-4" for="addFishingInstructor">Fishing Instructor</label>
        </div>
      </div>
      <form class="w-100"  @submit.prevent="signUp">
        <div class="form-floating input-group-width mb-2">
          <input id="firstNameInput" class="form-control" type="text" v-model="firstName" placeholder="First Name">
          <label for="firstNameInput">First Name</label>
        </div>
        <div class="form-floating input-group-width mb-2">
          <input id="lastNameInput" class="form-control" type="text" v-model="lastName" placeholder="Last Name">
          <label for="lastNameInput">Last Name</label>
        </div>
        <div class="form-floating input-group-width mb-2">
          <input id="emailInput" class="form-control" type="text" v-model="email" @blur="validateEmail" placeholder="E-mail">
          <label for="emailInput">E-mail</label>
        </div>
        <div class="form-floating input-group-width mb-2">
          <input id="usernameInput" class="form-control" type="text" v-model="username" placeholder="Username">
          <label for="usernameInput">Username</label>
        </div>
        <div class="form-floating input-group-width mb-2">
          <input id="passwordInput" class="form-control" type="password" v-model="password" placeholder="Password">
          <label for="passwordInput">Password</label>
        </div>
        <div class="form-floating input-group-width mb-2">
          <input id="confirmPasswordInput" class="form-control" type="password" v-model="confirmPassword" placeholder="Confirm Password">
          <label for="confirmPasswordInput">Confirm Password</label>
        </div>
        <div class="form-floating input-group-width mb-2">
          <input id="addressInput" class="form-control" type="text" v-model="address" placeholder="Address">
          <label for="addressInput">Address</label>
        </div>
        <div class="form-floating input-group-width mb-2">
          <input id="cityInput" class="form-control" type="text" v-model="city" placeholder="City">
          <label for="cityInput">City</label>
        </div>
        <div class="form-floating input-group-width mb-2">
          <country-select id="countryInput" class="form-control" v-model="country" :usei18n="false" :autocomplete="true"  :countryName="true"/>
          <label for="countryInput">Country</label>
        </div>
        <div class="form-floating input-group-width mb-2">
          <vue-tel-input id="phoneNumInput" class="form-control" v-model="phoneNum" @validate="telValidate"></vue-tel-input>
          <label for="phoneNumInput">Phone Number</label>
        </div>
        <div v-if="role!=='addClient'" class="form-floating input-group-width">
          <textarea id="reasonInput" class="form-control" v-model="registrationReason"></textarea>
          <label for="reasonInput">Reason for Registration</label>
        </div>

        <div>
          <button class="custom-btn button-secondary input-group-width mb-4 mt-5">Sign up</button>
        </div>
      </form>

    </div>
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
      username: '',
      email: '',
      password: '',
      confirmPassword: '',
      address: '',
      city: '',
      country: '',
      validNumber: '',
      phoneNum: null,
      registrationReason: '',

      role: 'addClient'
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

      


      if(this.username.length<3 || this.username.length > 20){
        this.$toast.error("Username must be between 3-20 characters");
        return;
      }
      if(!passwordRegex.test(this.username)){
        this.$toast.error("Username must only contain letters and numbers");
        return;
      }
      
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

      if(this.role !== 'addClient' && this.registrationReason === '') {
        this.$toast.error("Provide a reason for registering");
        return;
      }
      
      axios.get(`api/auth/isMailAvailable/${this.email}`).then(response => {
        if(response.data==false){
          this.$toast.error("Email already taken");
          return;
        }
        else{
 
          axios.get(`api/auth/isUsernameAvailable/${this.username}`).then(response => {
     
            if(response.data==false){
              this.$toast.error("Username already taken");
              return;
            }else{
                const user={'firstName':this.firstName,'lastName':this.lastName,'username':this.username,'email':this.email,'password':this.password,"confirmPassword":this.confirmPassword,
                'address':this.address,'city':this.city,'country':this.country,'phoneNum':this.validNumber};

                if (this.role !== "addClient") {
                  user.registrationReason = this.registrationReason;
                }

                axios.post(`/api/auth/${this.role}`, user).then(response => {
                  if(response.data==false){
                    this.$toast.error("Registration failed");
                  }else{
                    this.$toast.success("Registration successful");
                    this.$root.signUp=true;
                    this.$router.push("/");
                  }
                });

            }
          
          })         
          
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

    },
  
    }}
</script>

<style>
form {
  width:400px;
  margin:20px auto;
}

.radio {
  opacity: 0;
  width: 0;
  height: 0;
}

.registration-div {
  margin-top: 0;
  padding: 10px;
  border-radius: 5px;
  width: 45%;
  background-color: #00587a;
  align-content: center;
}

.input-group-width {
  width: 70%;
  margin: auto;
}

[type=radio]:checked + label {
  background-color: #004661;
}

</style>
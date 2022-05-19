<template>
  <head>
      <title>User Profile</title>
  </head>
  <body >     
    <div class="page">
      <div v-if="!this.editing" class="user-data-div">
        <img v-bind:src="'data:image/jpeg;base64,' + picture" style="width: 300px; height: 200px;">
        <br><br>
        <h1>{{user.firstName}} {{user.lastName}} </h1>        
        <p>E-mail: {{user.email}}</p>
        <p>Address: {{user.address}}</p>
        <p>City: {{user.city}}</p>
        <p>Country: {{user.country}}</p>
        <p>Phone Number: {{user.phoneNum}} </p>
        <p>Penalties: {{user.penalties}}</p>
      </div>
      <div v-else class="user-data-div">
        <img v-bind:src="'data:image/jpeg;base64,' + picture" style="width: 300px; height: 200px;">     
        <br><br>
        <input type="file" @change="onFileSelected" accept="image/png, image/jpeg" >
        <button class="btn btn-secondary" @click="onUpload">Upload</button>
        <br><br>
        <div class="input-group mb-3">
          <span class="input-group-text" id="firstName">First Name:</span>
          <input v-model="user.firstName" type="text" class="form-control" aria-label="Username" aria-describedby="firstName">
        </div>
        <div class="input-group mb-3">
          <span class="input-group-text" id="lastName">Last Name:</span>
          <input v-model="user.lastName" type="text" class="form-control" aria-label="Username" aria-describedby="lastName">
        </div>
        <div class="input-group mb-3">
          <span class="input-group-text" id="address">Address: </span>
          <input v-model="user.address" type="text" class="form-control" aria-label="Username" aria-describedby="address">
        </div>
        <div class="input-group mb-3">
          <span class="input-group-text" id="city">City:</span>
          <input v-model="user.city" type="text" class="form-control" aria-label="Username" aria-describedby="city">
        </div>
        <div>
          <country-select class="form-select" v-model="user.country" :usei18n="false" :autocomplete="true" :countryName="true"/>
        </div>
        <br>
        <vue-tel-input v-model="phoneNum" @validate="telValidate"></vue-tel-input>
      </div>
      <br>
      <button v-if="!this.editing" @click="toggleEdit()" class="btn btn-primary">Edit Info</button>
      <div v-else >
        <button style="margin-right: 20px" class="btn btn-primary" @click="saveEdit()">Save</button>
        <button class="btn btn-primary" @click="cancelEdit()">Cancel</button>
      </div>
      <button  class="btn btn-primary" @click="toggleDelete()">Delete My Account</button>
      <div v-if="deleting">
        MODAL FOR CREATING REQUEST<br>
        <textarea v-model="text" rows="4" cols="50" placeholder="Reason..."/>
        <button style="margin-right: 20px" class="btn btn-primary" @click="sendDeleteRequest()">Submit</button>
        <button class="btn btn-primary" @click="cancelDelete()">Cancel</button>
      </div>
    </div>

  </body>
</template>

<script>
import axios from "axios"




export default {
  name: "ClientProfile",
  data() {
    return {
      user:{},
      backUp:{},      
      selectedFile: null,
      picture: null,
      editing: false,
      validNumber: '',
      phoneNum: null,
      deleting: false,
      text: ""

            }
    },
     mounted() {
      axios
        .get("/api/clients/loggedClient",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} } )
        .then(response => (this.user = response.data)).catch((errorP) => {
        
        if(errorP.response.status===401){
          //this.$toast.error("Not Logged In");
          this.$root.accessToken=null;
          localStorage.setItem("jwt",null);
          
          this.$router.push("/loginPage");
        }
       
      });
      axios.get("/api/users/loggedUser/picture",{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => (this.picture=response.data)).catch((errorP) => {
        
        if(errorP.response.status===401){
          //this.$toast.error("Not Logged In");
          this.$root.accessToken=null;
          localStorage.setItem("jwt",null);
          
          this.$router.push("/loginPage");
        }
       
      });     
    },
    methods: {    
    toggleEdit(){
          this.backUp={"firstName":this.user.firstName,"lastName":this.user.lastName,"address":this.user.address,"city":this.user.city,"country":this.user.country,"phoneNum":this.user.phoneNum};
    
          this.editing=true; 
      },
    toggleDelete(){
      this.deleting=true;
    },
    sendDeleteRequest(){
      if(this.text.length<5){
        this.$toast.error("Provide a reason");
        return;
      }
      const request={"id":0,'submitterId':this.user.id,'submitterUsername':this.user.username,'text':this.text,'type':0};
      console.log(request);
      axios.post("/api/users/submitRequest",request,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => {if(response.data==true){this.$toast.success("Request Sent");}else{
          this.$toast.error("Error");
      }})
    },
    cancelDelete(){
      this.deleting=false;
    },
    saveEdit(){
      var regExp = /^[A-Za-z]+$/;
      
      if (this.user.firstName.length < 2 || this.user.firstName.length > 20) {
        this.$toast.error("First name must be between 2-20 characters");
        return;
        }
      if(regExp.test(this.user.firstName)==false){
        this.$toast.error("First name must contain only letters");
        return;
      }
      this.user.firstName=this.user.firstName.charAt(0).toUpperCase() + this.user.firstName.slice(1);
      
      if(this.user.lastName.length < 2 || this.user.lastName.length > 20){
        this.$toast.error("Last name must be between 2-20 characters");
        return;
      }
      if(regExp.test(this.user.lastName)==false){
        this.$toast.error("Last name must contain only letters");
        return;
      }
      this.user.lastName=this.user.lastName.charAt(0).toUpperCase() + this.user.lastName.slice(1);
      if(this.user.address === ''){
        this.$toast.error("Provide address");
        return;
      }
      if(this.user.city === ''){
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
          
      this.user.phoneNum=this.validNumber;
      
      axios.post("/api/clients/loggedClient",this.user,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => this.user=response.data ).then(this.toastMessage());
      this.editing=false;
    },
    telValidate(phoneNum) {
      if (phoneNum.valid) {
        this.validNumber = phoneNum.number;
      } else {
        this.validNumber = '';
      } 
    },
    cancelEdit(){
      this.user.firstName=this.backUp.firstName;
      this.user.lastName=this.backUp.lastName;
      this.user.address=this.backUp.address;
      this.user.city=this.backUp.city;
      this.user.country=this.backUp.country;
      this.user.phoneNum=this.backUp.phoneNum;
      this.editing=false;
    },
    toastMessage(){
      this.$toast.success("Info updated");
    },
    onFileSelected(event){
      this.selectedFile= event.target.files[0];
    },
    onUpload(){
      if(this.selectedFile == null){
          this.$toast.info("Select a file first ...");
      }else{
      if(this.selectedFile.size > 1048576){
        this.$toast.info("File is too large ...\nMust be smaller than 1Mb");
      }else{
      const fd=new FormData();
      fd.append('image',this.selectedFile,this.selectedFile.name);
      axios.post("/api/clients/loggedClient/picture",fd,{ headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} },{
        onUploadProgress: uploadEvent => {
          console.log("Upload Progress: "+ Math.round(uploadEvent.loaded/uploadEvent.total * 100) + '%');
        }
      }).then(response => (this.picture=response.data))
    }}
    }
  }

  
}
</script>

<style scoped>



</style>
<template>
  <head>
      <title>User Profile</title>
  </head>
  <body >     
    <div class="page">
      <div v-if="!this.editing" class="user-data-div">
        <img v-bind:src="'data:image/jpeg;base64,' + picture" style="width: 300px; height: 200px;">
        <h1>{{user.firstName}} {{user.lastName}} </h1>        
        <p>E-mail: {{user.email}}</p>
        <p>Address: {{user.address}}</p>
        <p>City: {{user.city}}</p>
        <p>Country: {{user.country}}</p>
        <p>Phone Number: {{user.phoneNum}} </p>
      </div>
      <div v-else class="user-data-div">  
        <img v-bind:src="'data:image/jpeg;base64,' + picture" style="width: 300px; height: 200px;">     
        <br> 
        <input  type="file" @change="onFileSelected" accept="image/png, image/jpeg" >
      
        <button @click="onUpload">Upload</button>
        <br> 
        <p>First Name: <input type="text" v-model="user.firstName" /></p>
        <p>Last Name: <input type="text" v-model="user.lastName" /></p>
        <p>E-mail: {{user.email}}</p>
        <p>Address: <input type="text" v-model="user.address" /></p>
        <p>City: <input type="text" v-model="user.city" /></p>        
        <div >
          <country-select v-model="user.country" :usei18n="false" :autocomplete="true" :countryName="true"/>        
        </div>
        <div>
          <vue-tel-input v-model="phoneNum" @validate="telValidate"></vue-tel-input>
        </div>
        
        
      </div>

      
      
        
      <button v-if="!this.editing" @click="toggleEdit()" class="edit-button">Edit Info</button>
      <div v-else >
        <button @click="saveEdit()">Save</button><br>
        <button @click="cancelEdit()">Cancel</button>
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
      phoneNum: null

            }
    },
     mounted() {
      axios
        .get("/api/clients/loggedClient" )
        .then(response => (this.user = response.data));
      axios.get("/api/clients/loggedClient/picture").then(response => (this.picture=response.data))      
    },
    methods: {    
    toggleEdit(){
          this.backUp={"firstName":this.user.firstName,"lastName":this.user.lastName,"address":this.user.address,"city":this.user.city,"country":this.user.country,"phoneNum":this.user.phoneNum};
    
          this.editing=true; 
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
      
      axios.put("/api/clients/loggedClient",this.user).then(response => this.user=response.data ).then(this.toastMessage());
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
      axios.post("/api/clients/loggedClient/picture",fd,{
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

.page{
  background-color: beige;
  display: flex;
  flex-direction: column;
  font-family: 'Poppins', sans-serif;
  align-items: center;
}

.edit-button{
  background-color: white;
  height: 5%;
  width: 8%;
  text-align-last: center;
}

.user-data-div{
  
  margin: 20px;
  border: 0.01px solid;
 
  display: flex;
  justify-content: center;
  background-color: white;  
  
  flex-direction: column;
  align-items: center;
  padding: 50px;
}

</style>
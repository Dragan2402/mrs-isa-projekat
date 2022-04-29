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
        <p>Country: <input type="text" v-model="user.country" /></p>
        <p>Phone Number: <input type="text" v-model="user.phoneNum" /></p>
        
        
      </div>

      
      
        
      <button v-if="!this.editing" @click="toggleEdit()" class="edit-button">Edit Info</button>
      <button v-else @click="saveEdit()" class="edit-button">Save</button>
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
      
      selectedFile: null,
      picture: null,
      editing: false,

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
          this.editing=true; 
      },
    saveEdit(){
      
      axios.put("/api/clients/loggedClient",this.user).then(response => this.user=response.data ).then(this.toastMessage());
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
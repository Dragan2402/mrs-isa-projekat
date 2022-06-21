<template>
  <div id="userDiv" v-if="loaded">
      <div>
          <img v-bind:src="'data:image/jpeg;base64,' + picture" style="width: 300px; height: 200px;" alt="...Profile picture...">
          <br><br>
          <h1 style="">{{user.firstName}} {{user.lastName}} </h1>        
          <p style="max-width: 250px;">E-mail: {{user.email}}</p>
          <p style="max-width: 250px;">Address: {{user.address}}</p>
          <p style="max-width: 250px;">City: {{user.city}}</p>
          <p style="max-width: 250px;">Country: {{user.country}}</p>
          <p style="max-width: 250px;">Phone Number: {{user.phoneNum}} </p>
      </div>
  </div>
</template>

<script>
import axios from "axios"
export default {
  name: "UserProfilePreview",
  data(){
    return{
        loaded:false,
        username: null,
        picture:null,
        user: {},
    }
  },
  mounted(){
    this.username =this.$route.params.username;
    axios.get(`/api/users/userPreview/${this.username}`).then(response => {this.user=response.data;this.loaded=true;}).catch((errorP) => {
        
        if(errorP.response.status===404){          
          this.$router.push({
            name: "notFoundPage",
          });
        }
       
      });  
    axios.get(`/api/users/userPreview/${this.username}/picture`).then(response => {this.picture=response.data})
  }


}
</script>

<style scoped>

#userDiv{
  display:flex;flex-direction:column;align-items: center;justify-content: center;margin-top:5%;
}

</style>
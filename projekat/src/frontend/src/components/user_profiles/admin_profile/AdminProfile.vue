<template>
<h2>OVO JE ADMIN PAGE</h2>

  {{complaints}}

   <div v-for="(complaint, index) in this.complaints" v-bind:index="index" :key="complaint.id">
     <div v-if="complaint.approved==false">{{complaint.text}}</div>


     <div><button class="custom-btn button-primary" @click="approveReport(complaint, index)">Approve</button></div>

   </div>


  <router-view></router-view>
</template>

<script>

import axios from "axios"

export default {
  name: "AdminProfile",
  data() {
    return {
      complaints:[]
    }
  },

  mounted() {
    axios.get("api/admins/complaints/all", { headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => (this.complaints = response.data))
  },

  methods: {
    approveReport(complaint, index) {
      axios.put("api/admins/complaints", complaint, { headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} }).then(response => {
        if(response.data==true){
          this.$toast.success("Approved");
          this.complaints.splice(index,1);
        }else{
          this.$toast.error("Problem has occurred!");
        }
      });
    }
  }
}
</script>

<style scoped>

</style>
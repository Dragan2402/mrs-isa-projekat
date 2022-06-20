<template>
  <div style="border: 1px solid black; margin: 10px; padding: 10px" v-for="(complaint, index) in this.complaints"
       v-bind:index="index" :key="complaint.id">
    <div>Text: {{ complaint.text }}</div>
    <div>Approved: {{ complaint.approved }}</div>
    <div>
      <button class="custom-btn button-primary" @click="approveComplaint(complaint, index)">Approve Complaint</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "AdminComplaints",
  data() {
    return {
      complaints: []
    }
  },
  mounted() {
    axios.get("api/admins/complaints/all", {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => (this.complaints = response.data))
  },
  methods: {
    approveComplaint(complaint, index) {
      axios.put("api/admins/complaints", complaint, {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => {
        if (response.data == true) {
          this.$toast.success("Approved");
          this.complaints[index].approved = true;
        } else {
          this.$toast.error("Problem has occurred!");
        }
      });
    }
  }
}
</script>

<style scoped>

</style>
<template>
  <div class="main-wrapper">
    <div class="for-container" v-for="(complaint, index) in this.complaints" v-bind:index="index" :key="complaint.id">
      <div class="inner-container">
        <div class="left-container">
          <div><b>Comment:</b> {{ complaint.text }}</div>
          <div><b>Approved:</b> {{ complaint.approved }}</div>
        </div>
        <div class="right-container">
          <button class="custom-btn button-primary" style="width: 120px" @click="approveComplaint(complaint, index)">Approve</button>
          <button class="custom-btn button-primary ms-5" style="width: 120px; background-color: indianred" @click="approveComplaint(complaint, index)">Reject</button>
        </div>
      </div>
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

.main-wrapper {
  margin-bottom: 20%;
}

.for-container {
  display: flex;
  justify-content: center;
  margin-top: 15px
}

.inner-container {
  border: 1px solid #00587a;
  display: flex;
  padding: 10px;
  border-radius: 5px
}

.left-container {
  text-align: left;
  width: 300px
}

.right-container {
  display: flex;
  margin: auto 100px auto 200px
}

</style>
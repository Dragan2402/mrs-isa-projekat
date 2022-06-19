<template>
  <h2>OVO JE ADMIN PAGE</h2>

  <div style="border: 1px solid black; margin: 10px; padding: 10px" v-for="(complaint, index) in this.complaints" v-bind:index="index" :key="complaint.id">
    <div>Text: {{ complaint.text }}</div>
    <div>Approved: {{ complaint.approved }}</div>
    <div>
      <button class="custom-btn button-primary" @click="approveReport(complaint, index)">Approve Complaint</button>
    </div>
  </div>
  <hr>
  <div style="border: 1px solid black; margin: 10px; padding: 10px" v-for="(review, index) in this.reviews" v-bind:index="index" :key="review.id">
    <div>Text: {{ review.comment }}</div>
    <div>Approved: {{ review.approved }}</div>
    <div>
      <button class="custom-btn button-primary" @click="approveReview(review, index)">Approve Review</button>
    </div>
  </div>
  <hr>
  <div style="border: 1px solid black; margin: 10px; padding: 10px" v-for="(request, index) in this.requests" v-bind:index="index" :key="request.id">
    <div>Text: {{ request.text }}</div>
    <div>Type: {{request.type}}</div>
    <div>
      <button class="custom-btn button-primary" @click="approveRequest(request, index)">Approve Request</button>
    </div>
  </div>


  <router-view></router-view>
</template>

<script>

import axios from "axios"

export default {
  name: "AdminProfile",
  data() {
    return {
      complaints: [],
      reviews: [],
      requests: [],
    }
  },

  mounted() {
    axios.get("api/admins/complaints/all", {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => (this.complaints = response.data))
    axios.get("api/admins/reviews/all", {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => (this.reviews = response.data))
    axios.get("api/admins/requests/all", {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => (this.requests = response.data))

  },

  methods: {
    approveReport(complaint, index) {
      axios.put("api/admins/complaints", complaint, {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => {
        if (response.data == true) {
          this.$toast.success("Approved");
          this.complaints[index].approved = true;
        } else {
          this.$toast.error("Problem has occurred!");
        }
      });
    },

    approveReview(review, index) {
      axios.put("api/admins/reviews", review, {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => {
        if (response.data == true) {
          this.$toast.success("Approved");
          this.reviews[index].approved = true;
        } else {
          this.$toast.error("Problem has occurred!");
        }
      });
    },

    approveRequest(request, index) {
      axios.put("api/admins/requests", request, {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => {
        if (response.data == true) {
          this.$toast.success("Deleted");
          this.requests[index].deleted = true;
        } else {
          this.$toast.error("Problem has occurred!");
        }
      });
    },
  }
}
</script>

<style scoped>

</style>
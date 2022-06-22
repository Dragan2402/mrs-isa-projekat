<template>
  <div class="main-wrapper">
    <div class="for-container" v-for="(review, index) in this.reviews" v-bind:index="index" :key="review.id">
      <div class="inner-container">
        <div class="left-container">
          <div v-if="review.comment"><b>Comment:</b> {{ review.comment }}</div>
          <div v-if="review.entityName"><b>Entity:</b> {{ review.entityName }}</div>
          <div v-if="review.ownerName "><b>Owner:</b> {{ review.ownerName }}</div>
          <div><b>Client:</b> {{ review.clientName }}</div>
        </div>
        <div class="right-container">
          <button class="custom-btn button-primary" style="width: 120px" @click="approveReview(review, index)">Approve</button>
          <button class="custom-btn button-primary ms-5" style="width: 120px; background-color: indianred" @click="rejectReview(review, index)">Reject</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "AdminReviews",
  data() {
    return {
      reviews: []
    }
  },
  mounted() {
    axios.get("https://renting-buddy-spring.herokuapp.com/api/admins/reviews/all", {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => (this.reviews = response.data))
  },
  methods: {
    approveReview(review, index) {
      axios.put("https://renting-buddy-spring.herokuapp.com/api/admins/reviews", review, {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => {
        if (response.data == true) {
          this.$toast.success("Approved");
          this.reviews.splice(index,1);
        } else {
          this.$toast.error("Problem has occurred!");
        }
      });
    },
    rejectReview(review, index) {
      axios.put("https://renting-buddy-spring.herokuapp.com/api/admins/reviews/reject", review, {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => {
        if (response.data == true) {
          this.$toast.success("Rejected!");
          this.reviews.splice(index,1);
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
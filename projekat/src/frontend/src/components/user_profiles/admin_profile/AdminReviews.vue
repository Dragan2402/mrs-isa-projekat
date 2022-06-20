<template>
  <div style="border: 1px solid black; margin: 10px; padding: 10px" v-for="(review, index) in this.reviews"
       v-bind:index="index" :key="review.id">
    <div>Text: {{ review.comment }}</div>
    <div>Approved: {{ review.approved }}</div>
    <div>
      <button class="custom-btn button-primary" @click="approveReview(review, index)">Approve Review</button>
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
    axios.get("api/admins/reviews/all", {headers: {"Authorization": `Bearer ${localStorage.getItem("jwt")}`}}).then(response => (this.reviews = response.data))
  },
  methods: {
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
  }
}
</script>

<style scoped>

</style>
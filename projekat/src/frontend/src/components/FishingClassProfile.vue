<template>
  <head>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <title>Fishing Class Profile</title>
  </head>
  <body>
  <div class="page">
    <div v-if="!this.editing" class="user-data-div">
      <p>Name: {{ fishingClass.name }}</p>
      <p>Address: {{ fishingClass.address }}</p>
      <p>Description: {{ fishingClass.promoDescription }}</p>
      <p>Instructor biography: {{ fishingClass.instructorBiography }}</p>
      <p>Behaviour Rules: {{ fishingClass.behaviourRules }}</p>
      <p>Price list: {{ fishingClass.priceList }}</p>
      <p>Client limit: {{ fishingClass.clientLimit }}</p>
      <p>Additional info: {{ fishingClass.additionalInfo }} </p>
      <p>Cancellation conditions: {{ fishingClass.cancellationConditions }}</p>
    </div>
    <div v-else class="user-data-div">
      <p>Name: <input type="text" v-model="fishingClass.name"/></p>
      <p>Address: <input type="text" v-model="fishingClass.address"/></p>
      <p>Description: <input type="text" v-model="fishingClass.promoDescription"/></p>
      <p>Instructor biography: <input type="text" v-model="fishingClass.instructorBiography"/></p>
      <p>Behaviour Rules: <input type="text" v-model="fishingClass.behaviourRules"/></p>
      <p>Price list: <input type="text" v-model="fishingClass.priceList"/></p>
      <p>Client limit: <input type="text" v-model="fishingClass.clientLimit"/></p>
      <p>Additional info: <input type="text" v-model="fishingClass.additionalInfo"/></p>
      <p>Cancellation conditions: <input type="text" v-model="fishingClass.cancellationConditions"/></p>
    </div>
    <br>
    <button v-if="!this.editing" @click="toggleEdit()" class="btn btn-primary">Edit Info</button>
    <button v-else @click="saveEdit()" class="btn btn-primary">Save</button>
    <br>
  </div>
  </body>
</template>

<script>
import axios from "axios"

export default {
  name: "FishingClassProfile",
  data() {
    return {
      fishingClass: {},
      editing: false
    }
  },
  mounted() {
    axios
        .get("/api/fishingClasses/2")
        .then(response => (this.fishingClass = response.data));
  },
  methods: {
    toggleEdit() {
      this.editing = true;
    },
    saveEdit() {
      axios.put("/api/fishingClasses/update", this.fishingClass).then(response => this.fishingClass = response.data).then(this.toastMessage());
      this.editing = false;
    },
    toastMessage() {
      this.$toast.success("Info updated");
    }
  }
}
</script>

<style scoped>

.page{

  text-align: center;
  margin: auto;
  padding: 50px;
  width: 50%;
}

.user-data-div{
  text-align: center;

  border-radius: 20px;
  background-color: lightgray;
  padding: 50px;

}


</style>
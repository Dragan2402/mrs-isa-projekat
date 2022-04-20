<template>
  <div class="page">
    <div v-if="!editing && !creatingOffer" class="user-data-div">
      <h1>{{vacationHouse.name}} </h1>
      <p>Address: {{vacationHouse.address}}</p>
      <p>Description: {{vacationHouse.promoDescription}}</p>
      <p>Behavior rules: {{vacationHouse.behaviourRules}}</p>
      <p>Price list: {{vacationHouse.priceList}}</p>
      <p>Additional info: {{vacationHouse.additionalInfo}}</p>
      <p>Cancellation conditions: {{vacationHouse.cancellationConditions}}</p>
      <p>Rooms: {{vacationHouse.roomsQuantity}}</p>
      <p>Beds per room: {{vacationHouse.bedsPerRoom}}</p>
      <p>Available from: {{ vacationHouse.availableFrom }}</p>
      <p>Available to: {{ vacationHouse.availableTo }}</p>

      <button @click="toggleEdit()" class="edit-button">Edit Info</button>
      <button @click="toggleCreatingOffer()" class="edit-button">Create Offer</button>
    </div>

    <div v-if="editing" class="user-data-div">
      <p>Name: <input type="text" v-model="vacationHouse.name"></p>
      <p>Address: <input type="text" v-model="vacationHouse.address"></p>
      <p>Description: <input type="text" v-model="vacationHouse.promoDescription"></p>
      <p>Behavior rules: <input type="text" v-model="vacationHouse.behaviourRules"></p>
      <p>Price list: <input type="text" v-model="vacationHouse.priceList"></p>
      <p>Additional info: <input type="text" v-model="vacationHouse.additionalInfo"></p>
      <p>Cancellation conditions: <input type="text" v-model="vacationHouse.cancellationConditions"></p>
      <p>Rooms: <input type="text" v-model="vacationHouse.roomsQuantity"></p>
      <p>Beds per room: <input type="text" v-model="vacationHouse.bedsPerRoom"></p>

      <Datepicker v-model="availabilityInterval" :format="formatRange" range/>
      <button @click="saveEdit()" class="edit-button">Save</button>
      <button @click="cancel()" class="edit-button">Cancel</button>
    </div>
    <div v-if="creatingOffer" class="user-data-div">
      <p>Place: <input type="text" v-model="offer.place"></p>
      <p>Client limit: <input type="text" v-model="offer.clientLimit"></p>
      <p>Price: <input type="text" v-model="offer.price"></p>

      <Datepicker v-model="offerInterval" :format="formatRange" range/>
      <button @click="createOffer()" class="edit-button">Save</button>
      <button @click="cancel()" class="edit-button">Cancel</button>
    </div>
  </div>

  <div class="container">
    <div class="row row-cols-4">
      <div class="col p-3" v-for="(picture, index) in pictures" :key="index">
        <img v-bind:src="'data:image/jpeg;base64,' + picture" style="width: 100%; height: auto;">
      </div>
    </div>
  </div>

</template>

<script>
import axios from "axios";
import { useRoute } from 'vue-router';
import {getCurrentInstance, onMounted, ref} from 'vue';
import moment from "moment";

export default {
  name: "VacationHouseProfile",
  setup() {
    const route = useRoute();
    const toast = getCurrentInstance().appContext.config.globalProperties.$toast;

    let id = ref('3');
    let vacationHouse = ref({
        id: null,
        name: null,
        address: null,
        promoDescription: null,
        availableFrom: null,
        availableTo: null,
        behaviourRules: null,
        priceList: null,
        additionalInfo: null,
        cancellationConditions: null,
        roomsQuantity: null,
        bedsPerRoom: null
    });
    let offer = ref({
      id: null,
      place: null,
      clientLimit: null,
      additionalServices: [],
      price: null,
      rentingEntityId: null,
      start : null,
      duration: null
    });
    let pictures = ref([]);
    let editing = ref(false);
    let creatingOffer = ref(false);
    let availabilityInterval = ref();
    let offerInterval = ref();

    onMounted(() => {
      if (route.params.id !== undefined) {
        id.value = route.params.id;
      }
      axios
        .get(`/api/vacation_houses/${id.value}`)
        .then(response => (vacationHouse.value = response.data));

      axios
        .get(`/api/vacation_houses/${id.value}/pictures/all`)
        .then(response => {
          for (let i = 0; i < response.data.length; i++) {
            pictures.value.push(response.data[i]);
          }
        });
    });

    const formatRange = (dates) => {
      let from = dates[0];
      let to = dates[1];

      const dayFrom = from.getDate();
      const monthFrom = from.getMonth() + 1;
      const yearFrom = from.getFullYear();
      const hourFrom = from.getHours();
      const minuteFrom = from.getMinutes();

      const dayTo = to.getDate();
      const monthTo = to.getMonth() + 1;
      const yearTo = to.getFullYear();
      const hourTo = to.getHours();
      const minuteTo = to.getMinutes();

      return `${dayFrom}.${monthFrom}.${yearFrom} ${hourFrom}:${minuteFrom} - ${dayTo}.${monthTo}.${yearTo} ${hourTo}:${minuteTo}`
    }

    function toggleEdit() {
      editing.value = true;
      let from = moment(vacationHouse.value.availableFrom, "dd.MM.yyyy HH:mm")
      let to = moment(vacationHouse.value.availableTo, "dd.MM.yyyy HH:mm")
      availabilityInterval.value = [from, to];
    }

    function toggleCreatingOffer() {
      creatingOffer.value = true;
    }

    function parseDate(date) {
      const day = ("0" + date.getDate()).slice(-2);
      const month = ("0" + (date.getMonth() + 1)).slice(-2);
      const year = date.getFullYear();
      const hours = ("0" + date.getHours()).slice(-2);
      const minutes = ("0" + date.getMinutes()).slice(-2);
      return `${day}.${month}.${year} ${hours}:${minutes}`;
    }

    function toastMessage() {
      toast.success("Info updated");
    }

    function saveEdit(){
      vacationHouse.value.availableFrom = parseDate(availabilityInterval.value[0]);
      vacationHouse.value.availableTo = parseDate(availabilityInterval.value[1]);

      axios
          .put(`/api/vacation_houses/${id.value}`, vacationHouse.value)
          .then(response => {
            vacationHouse.value = response.data;
            toastMessage();
          });

      editing.value = false;
    }

    function createOffer() {
      offer.value.start = parseDate(offerInterval.value[0]);
      offer.value.duration = offerInterval.value[1] - offerInterval.value[0];
      offer.value.rentingEntityId = id.value;

      axios
          .post("/api/offers/new", offer.value)
          .then(response => {
            offer.value = response.data;
            toastMessage();
          });

      creatingOffer.value = false;
    }

    function cancel() {
      editing.value = false;
      creatingOffer.value = false;
    }

    return {
      id,
      vacationHouse,
      offer,
      pictures,
      editing,
      creatingOffer,
      availabilityInterval,
      offerInterval,
      formatRange,
      toggleEdit,
      toggleCreatingOffer,
      parseDate,
      toastMessage,
      saveEdit,
      createOffer,
      cancel
    }
  }
}

</script>

<style scoped>

</style>
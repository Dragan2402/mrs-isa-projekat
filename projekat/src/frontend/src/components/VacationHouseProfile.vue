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
<!--      <p>Available from: {{ vacationHouse.availableFrom }}</p>-->
<!--      <p>Available to: {{ vacationHouse.availableTo }}</p>-->

      <div v-if="vacationHouse.availableFrom != null && vacationHouse.availableFrom != null">
        <label class="mb-3" for="calendar">Available dates</label><br>
        <v-calendar id="calendar" class="mb-3" :from-date="fromDate" :attributes="calendarAttributes"
                    :min-date="calendarAttributes[0].dates.start" :max-date="calendarAttributes[0].dates.end" /><br>
      </div>

      <button @click="toggleEdit()" style="margin-right: 20px;" class="btn btn-primary">Edit Info</button>
      <button @click="toggleCreatingOffer()" class="btn btn-primary">Create Offer</button>
    </div>

    <div v-if="editing" class="user-data-div">
      <div class="input-group mb-3">
        <span class="input-group-text" id="name">Name:</span>
        <input v-model="vacationHouse.name" type="text" class="form-control" aria-label="Username" aria-describedby="name">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="address">Address:</span>
        <input v-model="vacationHouse.address" type="text" class="form-control" aria-label="Username" aria-describedby="address">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="promoDescription">Description:</span>
        <input v-model="vacationHouse.promoDescription" type="text" class="form-control" aria-label="Username" aria-describedby="promoDescription">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="behaviourRules">Behaviour Rules:</span>
        <input v-model="vacationHouse.behaviourRules" type="text" class="form-control" aria-label="Username" aria-describedby="behaviourRules">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="priceList">Price list:</span>
        <input v-model="vacationHouse.priceList" type="text" class="form-control" aria-label="Username" aria-describedby="priceList">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="additionalInfo">Additional info:</span>
        <input v-model="vacationHouse.additionalInfo" type="text" class="form-control" aria-label="Username" aria-describedby="additionalInfo">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="cancellationConditions">Cancellation conditions:</span>
        <input v-model="vacationHouse.cancellationConditions" type="text" class="form-control" aria-label="Username" aria-describedby="cancellationConditions">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="roomsQuantity">Rooms:</span>
        <input v-model="vacationHouse.roomsQuantity" type="text" class="form-control" aria-label="Username" aria-describedby="roomsQuantity">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="bedsPerRoom">Beds per room:</span>
        <input v-model="vacationHouse.bedsPerRoom" type="text" class="form-control" aria-label="Username" aria-describedby="bedsPerRoom">
      </div>

<!--      <Datepicker v-model="availabilityInterval" :format="formatRange" range/>-->

      <v-date-picker v-model="availabilityInterval" :min-date="new Date()" mode="dateTime" is-range ></v-date-picker>
      <br>
      <button @click="saveEdit()" style="margin-right: 20px;" class="btn btn-primary">Save</button>
      <button @click="cancel()" class="btn btn-primary">Cancel</button>
    </div>
    <div v-if="creatingOffer" class="user-data-div">
      <div class="input-group mb-3">
        <span class="input-group-text" id="placeOffer">Place:</span>
        <input v-model="offer.place" type="text" class="form-control" aria-label="Username" aria-describedby="placeOffer">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="clientLimitOffer">Client limit:</span>
        <input v-model="offer.clientLimit" type="text" class="form-control" aria-label="Username" aria-describedby="clientLimitOffer">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text" id="priceOffer">Price:</span>
        <input v-model="offer.price" type="text" class="form-control" aria-label="Username" aria-describedby="priceOffer">
      </div>

      <Datepicker v-model="offerInterval" :format="formatRange" range/>
      <br>
      <button @click="createOffer()" style="margin-right: 20px;" class="btn btn-primary">Save</button>
      <button @click="cancel()" class="btn btn-primary">Cancel</button>
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
    let availabilityInterval = ref({
      start: undefined,
      end: undefined
    });
    let offerInterval = ref();

    let calendarAttributes = ref([
      {
        key: 'available',
        highlight: {
          start: { fillMode: 'outline' },
          base: { fillMode: 'light' },
          end: { fillMode: 'outline' }
        },
        dates: {
          start: undefined,
          end: undefined
        }
      }
    ]);
    let fromDate = ref();

    onMounted(() => {
      if (route.params.id !== undefined) {
        id.value = route.params.id;
      }
      axios
        .get(`/api/vacation_houses/${id.value}`, {headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`}})
        .then(response => {
          vacationHouse.value = response.data;
          let from = stringToDateTime(vacationHouse.value.availableFrom);
          let to = stringToDateTime(vacationHouse.value.availableTo);
          availabilityInterval.value.start = from;
          availabilityInterval.value.end = to;
          calendarAttributes.value[0].dates.start = from;
          calendarAttributes.value[0].dates.end = to;
          fromDate.value = availabilityInterval.value.start;
        });

      axios
        .get(`/api/vacation_houses/${id.value}/pictures/all`, {headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`}})
        .then(response => {
          for (let i = 0; i < response.data.length; i++) {
            pictures.value.push(response.data[i]);
          }
        });
    });

    function toggleEdit() {
      editing.value = true;
    }

    function toggleCreatingOffer() {
      creatingOffer.value = true;
    }

    function cancel() {
      editing.value = false;
      creatingOffer.value = false;
    }

    function saveEdit(){
      if(availabilityInterval.value.start != undefined && availabilityInterval.value.end != undefined) {
        vacationHouse.value.availableFrom = dateTimeToString(availabilityInterval.value.start);
        vacationHouse.value.availableTo = dateTimeToString(availabilityInterval.value.end);
        calendarAttributes.value[0].dates.start = availabilityInterval.value.start;
        calendarAttributes.value[0].dates.end = availabilityInterval.value.end;

        axios
            .put(`/api/vacation_houses/${id.value}`, vacationHouse.value, { headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`} })
            .then(response => {
              vacationHouse.value = response.data;
              toast.success("Info updated");
            })
            .catch(error => {
              if (error.response.status === 401) {
                toast.error("Error: Unauthorized access");
              } else if (error.response.status === 400) {
                toast.error("Error: Invalid data");
              }
            });

        editing.value = false;
      } else {
        toast.error("Error: Invalid dates")
      }
    }

    function createOffer() {
      offer.value.start = dateTimeToString(offerInterval.value[0]);
      offer.value.duration = offerInterval.value[1] - offerInterval.value[0];
      offer.value.rentingEntityId = id.value;

      axios
          .post("/api/offers/new", offer.value, {headers: {"Authorization" : `Bearer ${localStorage.getItem("jwt")}`}})
          .then(response => {
            offer.value = response.data;
            toast.success("Info updated");
          });

      creatingOffer.value = false;
    }

    function stringToDateTime(s) {
      let tokens = s.split(" ");
      let date = tokens[0];
      let time = tokens[1];

      tokens = date.split(".");
      let day = parseInt(tokens[0]);
      let month = parseInt(tokens[1]) - 1;
      let year = parseInt(tokens[2]);

      tokens = time.split(":");
      let hour = parseInt(tokens[0]);
      let minute = parseInt(tokens[1]);

      return new Date(year, month, day, hour, minute);
    }

    function dateTimeToString(dt) {
      let day = ("0" + dt.getDate()).slice(-2);
      let month = ("0" + (dt.getMonth() + 1)).slice(-2);
      let year = dt.getFullYear();
      let hour = ("0" + dt.getHours()).slice(-2);
      let minute = ("0" + dt.getMinutes()).slice(-2);
      return `${day}.${month}.${year} ${hour}:${minute}`;
    }

    // function parseDate(date) {
    //   const day = ("0" + date.getDate()).slice(-2);
    //   const month = ("0" + (date.getMonth() + 1)).slice(-2);
    //   const year = date.getFullYear();
    //   const hours = ("0" + date.getHours()).slice(-2);
    //   const minutes = ("0" + date.getMinutes()).slice(-2);
    //   return `${day}.${month}.${year} ${hours}:${minutes}`;
    // }

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

    return {
      id,
      vacationHouse,
      offer,
      pictures,
      editing,
      creatingOffer,
      availabilityInterval,
      offerInterval,
      calendarAttributes,
      fromDate,
      formatRange,
      toggleEdit,
      toggleCreatingOffer,
      // parseDate,
      stringToDateTime,
      dateTimeToString,
      saveEdit,
      createOffer,
      cancel
    }
  }
}

</script>

<style scoped>

</style>
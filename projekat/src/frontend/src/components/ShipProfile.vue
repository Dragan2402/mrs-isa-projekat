<template>
  <div class="page">
    <div v-if="!editing && !creatingOffer" class="user-data-div">
      <h1>{{ship.name}}</h1>
      <p>Address: {{ship.address}}</p>
      <p>Description: {{ship.promoDescription}}</p>
      <p>Behavior rules: {{ship.behaviourRules}}</p>
      <p>Price list: {{ship.priceList}}</p>
      <p>Additional info: {{ship.additionalInfo}}</p>
      <p>Cancellation conditions: {{ship.cancellationConditions}}</p>
      <p>Type: {{ship.type}}</p>
      <p>Length: {{ship.length}}</p>
      <p>Engine number: {{ship.engineNumber}}</p>
      <p>Engine power: {{ship.enginePower}}</p>
      <p>Top speed: {{ship.topSpeed}}</p>
      <p>Client limit: {{ship.clientLimit}}</p>
      <p>Available from: {{ ship.availableFrom }}</p>
      <p>Available to: {{ ship.availableTo }}</p>

      <button @click="toggleEdit()" class="edit-button">Edit Info</button>
      <button @click="toggleCreatingOffer()" class="edit-button">Create Offer</button>

      <div class="container">
        <div class="row row-cols-4">
          <div class="col p-3" v-for="(picture, index) in pictures" :key="index">
            <img v-bind:src="'data:image/jpeg;base64,' + picture" v-bind:alt="index" style="width: 100%; height: auto;">
          </div>
        </div>
      </div>

    </div>

    <div v-if="editing" class="user-data-div">
      <p>Name: <input type="text" v-model="ship.name"></p>
      <p>Address: <input type="text" v-model="ship.address"></p>
      <p>Description: <input type="text" v-model="ship.promoDescription"></p>
      <p>Behavior rules: <input type="text" v-model="ship.behaviourRules"></p>
      <p>Price list: <input type="text" v-model="ship.priceList"></p>
      <p>Additional info: <input type="text" v-model="ship.additionalInfo"></p>
      <p>Cancellation conditions: <input type="text" v-model="ship.cancellationConditions"></p>
      <p>Type: <input type="text" v-model="ship.type"></p>
      <p>Length: <input type="text" v-model="ship.length"></p>
      <p>Engine number: <input type="text" v-model="ship.engineNumber"></p>
      <p>Engine power: <input type="text" v-model="ship.enginePower"></p>
      <p>Top speed: <input type="text" v-model="ship.topSpeed"></p>
      <p>Client limit: <input type="text" v-model="ship.clientLimit"></p>

      <Datepicker v-model="availabilityInterval" :format="formatRange" range/>
      <button @click="saveEdit()" class="edit-button">Save</button>
      <button @click="cancel()" class="edit-button">Cancel</button>
    </div>

    <div v-if="creatingOffer" class="user-data-div">
      <p>Client limit: <input type="text" v-model="offer.clientLimit"></p>
      <p>Price: <input type="text" v-model="offer.price"></p>

      <p>Additional services: <vue3-tags-input :tags="offer.additionalServices"
                                               @on-tags-changed="updateTags"
                                               width="200px" /></p>

      <Datepicker v-model="offerInterval" :format="formatRange" range/>
      <button @click="createOffer()" class="edit-button">Save</button>
      <button @click="cancel()" class="edit-button">Cancel</button>
    </div>

  </div>
</template>

<script>
import axios from "axios";
import {useRoute} from "vue-router";
import {getCurrentInstance, onMounted, ref} from "vue";
import Vue3TagsInput from 'vue3-tags-input';

export default {
  name: "ShipProfile",
  components: {
    Vue3TagsInput
  },
  setup() {
    const route = useRoute();
    const toast = getCurrentInstance().appContext.config.globalProperties.$toast;

    let editing = ref(false);
    let creatingOffer = ref(false);

    let id = ref(1);
    let ship = ref({
      id: Number,
      name: String,
      address: String,
      promoDescription: String,
      availableFrom: String,
      availableTo: String,
      behaviourRules: String,
      priceList: String,
      additionalInfo: String,
      cancellationConditions: String,
      type: String,
      length: Number,
      engineNumber: String,
      enginePower: Number,
      topSpeed: Number,
      clientLimit: Number
    });
    let pictures = ref([]);
    let availabilityInterval = ref([]);

    let offer = ref({
      start: String,
      duration: Number,
      clientLimit: "",
      additionalServices: [],
      price: "",

      id: null,
      place: ship.value.address,
      rentingEntityId: ship.value.id
    });
    let offerInterval = ref([]);

    let tags = ref([]);

    onMounted(() => {
      if (route.params.id !== undefined) {
        id.value = route.params.id;
      }
      axios
          .get(`/api/ships/${id.value}`)
          .then(response => {
            ship.value = response.data;
            availabilityInterval.value = [stringToDateTime(ship.value.availableFrom),
                                          stringToDateTime(ship.value.availableTo)];
          });
      axios
          .get(`/api/ships/${id.value}/pictures/all`)
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
      ship.value.availableFrom = dateTimeToString(availabilityInterval.value[0]);
      ship.value.availableTo = dateTimeToString(availabilityInterval.value[1]);

      axios
          .put(`/api/ships/${id.value}`, ship.value)
          .then(response => {
            ship.value = response.data;
            toast.success("Info updated");
          });

      editing.value = false;
    }

    function createOffer() {
      offer.value.start = dateTimeToString(offerInterval.value[0]);
      offer.value.duration = offerInterval.value[1] - offerInterval.value[0];
      offer.value.place = ship.value.address;
      offer.value.rentingEntityId = parseInt(id.value);

      axios
          .post("/api/offers/new", offer.value)
          .then(response => {
            offer.value = response.data;
            toast.success("Info updated");
          });

      creatingOffer.value = false;
    }

    function updateTags(tags) {
      offer.value.additionalServices = tags;
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
      editing,
      creatingOffer,
      ship,
      pictures,
      availabilityInterval,
      offer,
      offerInterval,
      tags,
      toggleEdit,
      toggleCreatingOffer,
      cancel,
      saveEdit,
      createOffer,
      updateTags,
      formatRange
    }
  }
}
</script>

<style scoped>

</style>
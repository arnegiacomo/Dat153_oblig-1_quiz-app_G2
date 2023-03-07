# Dat153_oblig-1_quiz-app_G2

## DAT153 oblig 2

### Adding RoomDAO
We've added the RoomDAO so the data is stored even while shutting the app down.

### Testing

We've written three tests using Espresso.

#### Test 1
This test just launches the main activity, then it selects the quiz and clicks the first answer button. 

This one passed.

#### Test 2
This test launches the quiz and selects one right and one wrong answer while it checks that the score is correctly updated.

This one passed.

#### Test 3 
This test launches the database and adds an entry using intent scrubbing. The image it uploads is a sample.jpg from the drawable folder. The test then checks that the database has updated the amount of entries.

This one passed.

### Potential tests
We could have tested the following functionality:
- Sorting
- Deleting
- Difficulty
- Timer

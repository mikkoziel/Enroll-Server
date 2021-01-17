package GeneticAlgorithm;

import java.util.*;

public class Timetable {
    private final HashMap<Integer, Room> rooms;
    private final HashMap<Integer, Professor> professors;
    private final HashMap<Integer, Module> modules;
    private final HashMap<Integer, Student> students;
    private final HashMap<Integer, Timeslot> timeslots;
    private final HashMap<Integer, Preference> preferences ;

    public HashMap<Integer, Preference> getPreferences() {
        return preferences;
    }
    private ResultClass resultClasses[];
    private int numClasses = 0;

    public Timetable() {
        this.rooms = new HashMap<Integer, Room>();
        this.professors = new HashMap<Integer, Professor>();
        this.modules = new HashMap<Integer, Module>();
        this.students = new HashMap<Integer, Student>();
        this.timeslots = new HashMap<Integer, Timeslot>();
        this.preferences = new HashMap<Integer, Preference>();
    }

    public Timetable(Timetable cloneable) { // clone timetable
        this.rooms = cloneable.getRooms();
        this.professors = cloneable.getProfessors();
        this.modules = cloneable.getModules();
        this.students = cloneable.getStudents();
        this.timeslots = cloneable.getTimeslots();
        this.preferences = cloneable.getPreferences();
    }

    private HashMap<Integer, Student> getStudents() {
        return this.students;
    }

    private HashMap<Integer, Timeslot> getTimeslots() {
        return this.timeslots;
    }

    private HashMap<Integer, Module> getModules() {
        return this.modules;
    }

    private HashMap<Integer, Professor> getProfessors() {
        return this.professors;
    }

    public void addRoom(int roomId, String roomName, int capacity) {
        this.rooms.put(roomId, new Room(roomId, roomName, capacity));
    }

    public void addProfessor(int professorId, String professorName, String surname, String title) {
        this.professors.put(professorId, new Professor(professorId, professorName, surname, title));
    }

    public void addModule(int moduleId, String moduleCode, String module) { //, int professorIds[]
        this.modules.put(moduleId, new Module(moduleId, moduleCode, module)); //,professorIds
    }


    public void addStudent(int groupId, int moduleIds[]) {
        this.students.put(groupId, new Student(groupId, moduleIds));
        this.numClasses = 0;
    }

    public void addTimeslot(int timeslotId, DayAndHouDetails timeslot, int moduleId, int professorId, int groupSize) {
        this.timeslots.put(timeslotId, new Timeslot(timeslotId, timeslot, moduleId, professorId, groupSize));
    }

    public void addPreference(int preferenceId, int groupId, int timeslotId, int moduleId) {
        this.preferences.put(preferenceId, new Preference(preferenceId, groupId ,timeslotId, moduleId));
    }

    /**
     * Create classes using individual's chromosome
     *
     * One of the two important methods in this class; given a chromosome,
     * unpack it and turn it into an array of Class (with a capital C) objects.
     * These Class objects will later be evaluated by the calcClashes method,
     * which will loop through the Classes and calculate the number of
     * conflicting timeslots, rooms, professors, etc.
     *
     * While this method is important, it's not really difficult or confusing.
     * Just loop through the chromosome and create Class objects and store them.
     *
     * @param individual
     */
    public void createClasses(Individual individual) {
        // Init classes
        ResultClass resultClasses[] = new ResultClass[this.getNumClasses()];

        // Get individual's chromosome
        int chromosome[] = individual.getChromosome();
        int chromosomePos = 0;
        int classIndex = 0;

        for (Student student : this.getGroupsAsArray()) {
            int moduleIds[] = student.getModuleIds();
            for (int moduleId : moduleIds) {
                resultClasses[classIndex] = new ResultClass(classIndex, student.getStudentId(), moduleId);

                // Add timeslot
                resultClasses[classIndex].addTimeslot(chromosome[chromosomePos]);
                chromosomePos++;

                // Add room
                resultClasses[classIndex].setRoomId(chromosome[chromosomePos]);
                chromosomePos++;

                resultClasses[classIndex].increaseGroupSize();

                // Add professor
//                classes[classIndex].addProfessor(chromosome[chromosomePos]);
//                chromosomePos++;

                classIndex++;
            }
        }

        this.resultClasses = resultClasses;
    }

    public Room getRoom(int roomId) {
        if (!this.rooms.containsKey(roomId)) {
            System.out.println("Rooms doesn't contain key " + roomId);
        }
        return (Room) this.rooms.get(roomId);
    }

    public HashMap<Integer, Room> getRooms() {
        return this.rooms;
    }

    public Room getRandomRoom() {
        Object[] roomsArray = this.rooms.values().toArray();
        Room room = (Room) roomsArray[(int) (roomsArray.length * Math.random())];
        return room;
    }

    public Professor getProfessor(int professorId) {
        return (Professor) this.professors.get(professorId);
    }

    public Module getModule(int moduleId) {
        return (Module) this.modules.get(moduleId);
    }

    public int[] getGroupModules(int groupId) {
        Student student = (Student) this.students.get(groupId);
        return student.getModuleIds();
    }

    public Student getGroup(int groupId) {
        return (Student) this.students.get(groupId);
    }

    public Student[] getGroupsAsArray() {
        return (Student[]) this.students.values().toArray(new Student[this.students.size()]);
    }

    public Timeslot getTimeslot(int timeslotId) {
        return (Timeslot) this.timeslots.get(timeslotId);
    }

    public Timeslot getRandomTimeslot(int moduleId) {
        Object[] timeslotArray = this.timeslots.values().stream().filter(a -> a.getModuleId() == moduleId).toArray();
        int timeslotLength = timeslotArray.length;
//        Timeslot timeslot = (Timeslot) timeslotArray[0];
        Random r = new Random();
        int low = 0;
        int high = timeslotLength;//-1;
       // int result = r.nextInt(high-low) + low;
        int result = (int) (Math.random()* (high - low) + low);
        Timeslot timeslot = (Timeslot) timeslotArray[(int) (result)];
        //Timeslot timeslot = (Timeslot) timeslotArray[(int) (timeslotArray.length * Math.random())];
        return timeslot;
    }

    public ResultClass[] getClasses() {
        return this.resultClasses;
    }

    public int getNumClasses() {
        if (this.numClasses > 0) {
            return this.numClasses;
        }

        int numClasses = 0;
        Student students[] = (Student[]) this.students.values().toArray(new Student[this.students.size()]);
        for (Student student : students) {
            numClasses += student.getModuleIds().length;
        }
        this.numClasses = numClasses;

        return this.numClasses;
    }

    /**
     * Calculate the number of clashes between Classes generated by a
     * chromosome.
     *
     * The most important method in this class; look at a candidate timetable
     * and figure out how many constraints are violated.
     *
     * Running this method requires that createClasses has been run first (in
     * order to populate this.classes). The return value of this method is
     * simply the number of constraint violations (conflicting professors,
     * timeslots, or rooms), and that return value is used by the
     * GeneticAlgorithm.calcFitness method.
     *
     * There's nothing too difficult here either -- loop through this.classes,
     * and check constraints against the rest of the this.classes.
     *
     * The two inner `for` loops can be combined here as an optimization, but
     * kept separate for clarity. For small values of this.classes.length it
     * doesn't make a difference, but for larger values it certainly does.
     *
     * @return numClashes
     */
    public int  calcClashes() {
        int clashes = 0;
        int peopleInATimeSlot[] = new int[this.getTimeslots().values().size() + 1]; // tyle ile mamy timeslot
        for (int p = 0; p < peopleInATimeSlot.length; p++) {
            peopleInATimeSlot[p] = 0;
        }

        int index = 0;
        for (ResultClass resultClassA : this.resultClasses) {
            index++;
            // Check room capacity
            int roomCapacity = this.getRoom(resultClassA.getRoomId()).getRoomCapacity();
//            int groupSize = this.getGroup(classA.getGroupId()).getGroupSize();
//
//            if (roomCapacity < groupSize) {
//                clashes++;
//

            // Check if actualGroupSize doesnt exceed maxGroupSize
            Timeslot timeslot = this.getTimeslots().values().stream().filter(a -> a.getTimeslotId() == resultClassA.getTimeslotId()).
                    findFirst().get();

            peopleInATimeSlot[timeslot.getTimeslotId()]++; // index od 0

            // Check if room is taken
//            for (Class classB : this.classes) {
//                if (classA.getRoomId() == classB.getRoomId() && classA.getTimeslotId() == classB.getTimeslotId()
//                        && classA.getClassId() != classB.getClassId()) {
//                    clashes++;
//                    break;
//                }
//            }

            // Check if professor is available
//            for (Class classB : this.classes) {
//                if (classA.getProfessorId() == classB.getProfessorId() && classA.getTimeslotId() == classB.getTimeslotId()
//                        && classA.getClassId() != classB.getClassId()) {
//                    clashes++;
//                    break;
//                }
//            }
            // SPRAWDZIENIE LICZEBNOSCI GRUPY
            if (this.getStudents().values().size() == index) {
                for (int p = 1; p < peopleInATimeSlot.length; p++) {
                    int index2 = p;
                    Timeslot timeslot1 = this.timeslots.values().stream().filter(r -> r.getTimeslotId() == index2).findFirst().get();
                    if (peopleInATimeSlot[p] > timeslot1.getMaxGroupSize()) {
                        clashes++;
                    }
                }

//                for (int a : peopleInATimeSlot) {
//                    if (a > timeslot.getMaxGroupSize()) {
//                        clashes++;
//                    }
                index = 0;
            }

        }
        for(Object student: this.students.values().stream().map(a -> a.getStudentId()).toArray()){
        // SPRAWDZIC CZY TIMESLOTY NIE POKRYWAJA SIE DLA TEGO SAMEGO STUDENTA
        Object[] objects = Arrays.stream(this.resultClasses).filter(a -> a.getGroupId() == 1).toArray();
        Object[] objects1 = Arrays.stream(this.resultClasses).filter(a -> a.getGroupId() == 1).map(a -> a.getTimeslotId()).toArray();
        Object[] objects2 = Arrays.stream(this.resultClasses).filter(a -> a.getGroupId() == (int) student).map(a -> a.getTimeslotId()).
                map(a -> this.timeslots.values().stream().filter(b -> b.getTimeslotId() == a).
                        findFirst().get().getDayAndHourDetails()).toArray();

        Set<DayAndHouDetails> duplicateNameSet = new HashSet<DayAndHouDetails>();

        for (int i = 0; i < objects2.length - 1; i++)
            for (int j = i + 1; j < objects2.length; j++) {
                if (objects2[i].equals(objects2[j])) {
                    duplicateNameSet.add((DayAndHouDetails) objects2[j]);
                }
            }
        if (!duplicateNameSet.isEmpty()) {
            //System.out.println("There are duplicate names. These names are: " + duplicateNameSet);
            int size = duplicateNameSet.size();
            for (int k = 0; k < size; k++)
                clashes++;
        }
    }

        return clashes;
    }

    public int calcMatchingPreferences() {
        int matches = 0;

        for (ResultClass resultClassA : this.resultClasses) {
            Optional<Preference> first = this.getPreferences().values().stream().filter(a -> a.getStudentId() == resultClassA.getGroupId() &&
                    a.getTimeslotId() == resultClassA.getTimeslotId()).findFirst();
            if(first.isPresent()) {
                matches++;
            }
        }
        return matches;
    }
}

public class Parser {

    public static Task parseTask(String input) {
        String[] words = input.split(" ", 2);
        String command = words[0];

        switch (command) {
            case "todo":
                if (words.length < 2 || words[1].isEmpty()) {
                    throw new IllegalArgumentException("The description of a todo cannot be empty.");
                }
                return new Todo(words[1].trim());

            case "deadline":
                String[] parts = words[1].split("/by");
                if (parts.length < 2) {
                    throw new IllegalArgumentException("Deadline must have a /by clause.");
                }
                return new Deadline(parts[0].trim(), parts[1].trim());

            case "event":
                String[] timeParts = words[1].split("/from");
                if (timeParts.length < 2) {
                    throw new IllegalArgumentException("Event must have a /from and /to clause.");
                }
                String description = timeParts[0].trim();
                String[] fromTo = timeParts[1].split("/to", 2);
                if (fromTo.length < 2) {
                    throw new IllegalArgumentException("Event must have a /to clause.");
                }
                return new Event(description, fromTo[0].trim(), fromTo[1].trim());

            default:
                throw new IllegalArgumentException("I'm sorry, but I don't know what that means...");
        }
    }
}

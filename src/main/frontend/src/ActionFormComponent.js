import { useState } from "react";

export default function ActionFormComponent({ setGameboard }) {

    const [message, setMessage] = useState(null);

    async function submitAction(e) {

        e.preventDefault();

        const commandValue = e.target.command.value;

        if (!['w', 'd', 's', 'a', 'f', 'c'].includes(commandValue)) {
            setMessage('Invalid input. Please use from the available commands.');
            return;
          }

        const response = await fetch("http://localhost:8080/game", {
            method: 'PUT',
            headers: { 'content-type': 'application/json' },
            body: JSON.stringify({ command: commandValue })
        });

        const json = await response.json();

 //       .then(response => response.json())
 //       .then(data => setGameboard(data));

        if (response.status == 200) {
            setGameboard(json);
        } else {
            setMessage(json);
        }
        console.log(message);
        setMessage(null);
        e.target.command.value = "";
    };

    return (
        <div>
            <form onSubmit={submitAction}>
                <label>Make action:</label>
                <input type="text" id="command" maxLength="1" />
                {message && <p>{message}</p>}
                <input type="submit" value="Submit" />
            </form>
        </div>
    );
}
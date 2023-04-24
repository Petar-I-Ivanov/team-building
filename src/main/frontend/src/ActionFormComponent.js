import { useState } from "react";

export default function ActionFormComponent({ setGameboard }) {

    const [message, setMessage] = useState(null);

    async function submitAction(e) {

        e.preventDefault();

        const actionValue = e.target.action.value;

        if (!['w', 'd', 's', 'a', 'f', 'c'].includes(actionValue)) {
            setMessage('Invalid input. Please use from the available commands.');
            return;
          }

        await fetch("http://localhost:8080/game", {
            method: 'PUT',
            headers: { 'content-type': 'application/json' },
            body: JSON.stringify({ action: actionValue })
        })

        .then(response => response.json())
        .then(data => setGameboard(data));

        setMessage(null);
        e.target.action.value = "";
    };

    return (
        <div>
            <form onSubmit={submitAction}>
                <label>Make action:</label>
                <input type="text" id="action" maxLength="1" />
                {message && <p>{message}</p>}
                <input type="submit" value="Submit" />
            </form>
        </div>
    );
}
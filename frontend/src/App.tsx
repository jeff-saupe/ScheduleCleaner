import React, { useState } from "react";
import {
	Box,
	Button,
	ButtonGroup,
	CssBaseline,
	Snackbar,
	Tooltip
} from "@material-ui/core";
import MuiAlert from "@material-ui/lab/Alert";
import Header from "./components/Header";
import Configurator from "./components/Configurator";
import copy from "copy-to-clipboard";
import useStyles from "./styles/ConfiguratorStyles";

function downloadICS(icsURL: string, name: string) {
	const anchor = document.createElement("a");
	anchor.setAttribute("download", name);
	anchor.setAttribute("target", "_blank");
	anchor.href = icsURL;
	document.body.appendChild(anchor);
	anchor.click();
	anchor.remove();
}

function App() {
	const classes = useStyles();
	const [icsURL, setIcsURL] = useState<string | null>(null);
	const [icsName, setIcsName] = useState<string>("");
	const [copied, setCopied] = useState({
		open: false,
		status: false,
		message: "",
	});

	function generateIcsURL(icsURL: string): void {
		if (copy(icsURL))
			setCopied({
				open: true,
				status: true,
				message: "Copied URL to the clipboard.",
			});
		else
			setCopied({
				open: true,
				status: false,
				message: "Failed to copy URL to the clipboard!",
			});
	}
	return (
		<>
			<CssBaseline />
			<main>
				<Header />

				<Box display="flex" justifyContent="center" alignItems="center" m={2}>
					<ButtonGroup>
						<Tooltip title="Copies the URL into your clipboard" arrow>
							<Button
								classes={{
									root: classes.enabledButton,
									disabled: classes.disabledButton
								}}
								color="primary"
								disabled={icsURL === null}
								onClick={() => generateIcsURL(icsURL || "")}
							>
								Generate URL
							</Button>
						</Tooltip>

						<Button
							classes={{
								root: classes.enabledButton,
								disabled: classes.disabledButton
							}}
							color="primary"
							disabled={icsURL === null}
							onClick={downloadICS.bind(null, icsURL || "", icsName)}
						>
							Download ICS
						</Button>
					</ButtonGroup>
				</Box>

				<Configurator setIcsURL={setIcsURL} setIcsName={setIcsName} />

				<Box display="flex" justifyContent="center" alignItems="center" m={3}>
					<Button
						href="https://github.com/jeff-saupe/ScheduleCleaner#%EF%B8%8F-schedule-cleaner"
						variant="outlined"
						color="primary"
					>
						This project on Github
					</Button>
				</Box>
			</main>

			<Snackbar
				anchorOrigin={{ vertical: "bottom", horizontal: "right" }}
				open={copied.open}
				onClose={() => setCopied({ ...copied, open: false })}
				autoHideDuration={5000}
			>
				<MuiAlert
					elevation={6}
					variant="filled"
					severity={copied.status ? "success" : "error"}
				>
					{copied.message}
				</MuiAlert>
			</Snackbar>
		</>
	);
}

export default App;

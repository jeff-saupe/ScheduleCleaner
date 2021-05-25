import { makeStyles } from "@material-ui/core";

const useStyles = makeStyles((theme) => ({
	divider: {
		margin: "10px 0px",
	},
	listInput: {
		marginRight: 10,
	},
	listSubheader: {
		display: "flex",
		justifyContent: "space-between",
		alignItems: "center",
	},
	listItemText: {
		color: "white",
		cursor: "default"
	},
	enabledButton: {
		fontWeight: "bold"
	},
	disabledButton: {
		fontWeight: "normal"
	}
}));

export default useStyles;

import { Box, Grid, Typography } from "@material-ui/core";
import React from "react";
import useStyles from "../styles/HeaderStyles";

export default function Header() {
	const classes = useStyles();
	return (
		<Box p={2}>
			<Grid className={classes.header} container spacing={4}>
				<Grid item>
					<img
						className={classes.headerImg}
						src="assets/scheduleCleaner.svg"
						alt="Schedule Cleaner"
					/>
				</Grid>
				<Grid item className={classes.headerText}>
					<Typography variant="h5" color="textSecondary">
						Filter unnecessary junk from your NAK Schedule.
					</Typography>
				</Grid>
			</Grid>
		</Box>
	);
}

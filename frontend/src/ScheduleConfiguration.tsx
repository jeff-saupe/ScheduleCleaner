import {
	Box,
	Checkbox,
	Container,
	Divider,
	Grid,
	List,
	ListItem,
	ListItemIcon,
	ListItemSecondaryAction,
	ListItemText,
	MenuItem,
	Paper,
	TextField,
	Typography,
} from "@material-ui/core";
import React, { useEffect, useState } from "react";
import ExcludeList from "./ExcludeList";
import ReplaceList from "./ReplaceList";
import { v4 as uuid } from "uuid";
import validate from "./validate";
import buildURL from "./buildURL";
import useStyles from "./ScheduleConfigurationStyles";
import { LocationOn } from "@material-ui/icons";

const semesters = [1, 2, 3, 4, 5, 6, 7];

export default function ScheduleConfiguration(props: {
	setIcsURL: any;
	setIcsName: any;
}) {
	const classes = useStyles();
	const [centuria, setCenturia] = useState<string>("");
	const [semester, setSemester] = useState<string>("");
	const [exclude, setExclude] = useState([]);
	const [replace, setReplace] = useState([]);
	const [fixRoom, setFixRoom] = useState(true);

	useEffect(() => {
		props.setIcsURL(buildURL(centuria, semester, exclude, replace, fixRoom));
		props.setIcsName(`${centuria}_${semester}`);
	}, [centuria, semester, exclude, replace, fixRoom, props]);

	return (
		<Container maxWidth="sm">
			<Paper elevation={3} style={{ padding: 16 }}>
				<Typography align="center" variant="h5" color="textSecondary">
					Configure your schedule
				</Typography>
				<Box mb={2} mt={1}>
					<Grid container alignItems="flex-start" spacing={3}>
						<Grid item xs={6}>
							<TextField
								margin="dense"
								variant="outlined"
								label="Centuria"
								name="centuria"
								fullWidth
								required
								value={centuria}
								error={!validate.centuria(centuria)}
								onChange={(e) => setCenturia(e.target.value)}
							/>
						</Grid>
						<Grid item xs={6}>
							<TextField
								type="number"
								margin="dense"
								variant="outlined"
								select
								label="Semester"
								name="semester"
								fullWidth
								required
								value={semester}
								error={!validate.semester(semester)}
								onChange={(e) => setSemester(e.target.value.toString())}
							>
								{semesters.map((semester) => (
									<MenuItem key={semester} value={semester}>
										{semester}
									</MenuItem>
								))}
							</TextField>
						</Grid>
					</Grid>
				</Box>

				<Divider className={classes.divider} />

				<List>
					<ListItem>
						<ListItemIcon>
							<LocationOn />
						</ListItemIcon>
						<ListItemText>Set the room as the event's location</ListItemText>
						<ListItemSecondaryAction>
							<Checkbox
								edge="end"
								checked={fixRoom}
								color="primary"
								onChange={(val: any) => setFixRoom(val.target.checked)}
							/>
						</ListItemSecondaryAction>
					</ListItem>
				</List>
				
				<Divider className={classes.divider} />

				<ExcludeList exclude={exclude} setExclude={setExclude} />

				<Divider className={classes.divider} />

				<ReplaceList replace={replace} setReplace={setReplace} />

			</Paper>
		</Container>
	);
}
